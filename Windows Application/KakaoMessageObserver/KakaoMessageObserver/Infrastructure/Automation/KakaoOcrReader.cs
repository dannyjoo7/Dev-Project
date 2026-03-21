using KakaoMessageObserver.Domain.Interfaces;
using KakaoMessageObserver.Infrastructure.Hook;
using System.Drawing;
using System.Drawing.Imaging;
using System.Text;
using OpenCvSharp;
using OpenCvSharp.Extensions;
using Sdcb.PaddleOCR;
using Sdcb.PaddleOCR.Models.Local;

namespace KakaoMessageObserver.Infrastructure.Automation
{
    /// <summary>
    /// PaddleOCR을 사용하여 카카오톡 화면으로부터 텍스트 정보를 추출하는 클래스입니다.
    /// </summary>
    public class KakaoOcrReader : IKakaoOcrReader, IDisposable
    {
        // PaddleOCR의 모든 모델(인식, 검출, 분류)을 포함하는 통합 엔진 필드
        private readonly PaddleOcrAll _engine;

        /// <summary>
        /// 클래스 생성자: 한국어 V4 로컬 모델을 로드하여 OCR 엔진을 초기화합니다.
        /// </summary>
        public KakaoOcrReader()
        {
            // LocalFullModels.KoreanV4: 한글 인식에 최적화된 딥러닝 모델 사용
            _engine = new PaddleOcrAll(LocalFullModels.KoreanV4)
            {
                // 회전 감지 비활성화 (채팅창은 항상 정방향이므로 성능 향상을 위해 false 설정)
                AllowRotateDetection = false
            };
        }

        /// <summary>
        /// 지정된 윈도우 핸들에서 텍스트를 읽어 줄바꿈 문자로 연결된 문자열을 반환합니다.
        /// </summary>
        /// <param name="mainWindowHwnd">카카오톡 메인 창 핸들</param>
        /// <returns>인식된 전체 대화 텍스트</returns>
        public string ReadFromWindow(IntPtr mainWindowHwnd)
        {
            var results = ReadDetailedText(mainWindowHwnd);
            // 상세 결과 리스트에서 텍스트만 뽑아 줄바꿈으로 합침
            return string.Join(Environment.NewLine, results.Select(r => r.Text));
        }

        /// <summary>
        /// 채팅 영역을 캡처하고 OCR을 실행하여 좌표 정보가 포함된 상세 결과를 추출합니다.
        /// </summary>
        /// <param name="mainWindowHwnd">카카오톡 메인 창 핸들</param>
        /// <returns>좌표 및 텍스트 정보가 담긴 OcrResult 리스트</returns>
        public List<OcrResult> ReadDetailedText(IntPtr mainWindowHwnd)
        {
            var results = new List<OcrResult>();

            // 1. 실제 메시지가 담긴 내부 자식 창(EVA_Window 등)의 핸들을 탐색
            var chatListHwnd = FindChatContentWindow(mainWindowHwnd);
            if (chatListHwnd == IntPtr.Zero) return results;

            // 2. 해당 영역 캡처 (GDI 방식)
            using var bmp = CaptureWindow(chatListHwnd);
            if (bmp == null) return results;

            // 3. OpenCV 전처리: PaddleOCR은 3채널(BGR) 이미지를 요구함
            using var mat4 = bmp.ToMat(); // 캡처된 4채널(BGRA) Mat 생성
            using var mat = new Mat();    // 3채널을 저장할 빈 Mat 생성

            // BGRA(4채널)에서 A(알파) 채널을 제거하여 BGR(3채널)로 변환
            Cv2.CvtColor(mat4, mat, ColorConversionCodes.BGRA2BGR);

            // 4. PaddleOCR 엔진 실행 (딥러닝 추론 수행)
            PaddleOcrResult ocrResult = _engine.Run(mat);

            // 5. 결과 정렬: OCR은 인식 순서가 무작위일 수 있으므로, Y좌표(위->아래) 순으로 정렬
            var sortedRegions = ocrResult.Regions
                .OrderBy(r => r.Rect.Center.Y)
                .ToList();

            // 6. 도메인 모델(OcrResult)로 변환 및 필터링
            foreach (var region in sortedRegions)
            {
                // 공백이거나 인식 신뢰도(Score)가 50% 미만인 데이터는 노이즈로 간주하여 제외
                if (string.IsNullOrWhiteSpace(region.Text) || region.Score < 0.5f) continue;

                results.Add(new OcrResult
                {
                    Text = region.Text.Trim(),
                    // 텍스트 박스의 중심점에서 가로 폭의 절반을 빼서 시작 X 좌표 계산 (int 캐스팅 필수)
                    X = (int)(region.Rect.Center.X - (region.Rect.Size.Width / 2.0f)),
                    PageWidth = (int)mat.Width
                });
            }

            return results;
        }

        #region ===== Window Capture & Find =====

        /// <summary>
        /// 카카오톡 메인 창 내부에서 실제 대화 내용이 렌더링되는 클래스(EVA_Window, RichEdit)를 탐색합니다.
        /// </summary>
        private IntPtr FindChatContentWindow(IntPtr mainHwnd)
        {
            IntPtr result = IntPtr.Zero;
            // Win32 API를 사용하여 모든 자식 윈도우를 열거
            NativeMethods.EnumChildWindows(mainHwnd, (hWnd, lParam) =>
            {
                var sb = new StringBuilder(256);
                NativeMethods.GetClassName(hWnd, sb, sb.Capacity);

                // 클래스 이름에 EVA(구버전/공지) 또는 RichEdit(현재 채팅창)가 포함된 경우 타겟으로 선정
                if (sb.ToString().Contains("EVA") || sb.ToString().Contains("RichEdit"))
                {
                    result = hWnd;
                    return false; // 찾았으므로 열거 중단
                }
                return true; // 계속 탐색
            }, IntPtr.Zero);
            return result;
        }

        /// <summary>
        /// Win32 BitBlt를 사용하여 특정 핸들의 영역을 비트맵으로 캡처합니다.
        /// </summary>
        private Bitmap? CaptureWindow(IntPtr hwnd)
        {
            if (!NativeMethods.GetWindowRect(hwnd, out var rect)) return null;
            if (rect.Width <= 0 || rect.Height <= 0) return null;

            // 32비트 ARGB 포맷의 비트맵 메모리 할당
            var bmp = new Bitmap(rect.Width, rect.Height, PixelFormat.Format32bppArgb);
            using var g = Graphics.FromImage(bmp);
            using var screen = Graphics.FromHwnd(IntPtr.Zero); // 데스크톱 화면 기준 디바이스 컨텍스트

            IntPtr hdcDest = g.GetHdc();      // 복사 대상(비트맵) DC
            IntPtr hdcSrc = screen.GetHdc();  // 복사 원본(화면) DC

            // 화면의 좌표(rect.Left, rect.Top)부터 윈도우 크기만큼 메모리 비트맵으로 픽셀 복사
            NativeMethods.BitBlt(hdcDest, 0, 0, rect.Width, rect.Height, hdcSrc, rect.Left, rect.Top, NativeMethods.SRCCOPY);

            g.ReleaseHdc(hdcDest);
            screen.ReleaseHdc(hdcSrc);

            return bmp;
        }
        #endregion

        /// <summary>
        /// 비관리 리소스(PaddleOCR 엔진)를 해제합니다.
        /// </summary>
        public void Dispose()
        {
            _engine?.Dispose();
        }
    }
}