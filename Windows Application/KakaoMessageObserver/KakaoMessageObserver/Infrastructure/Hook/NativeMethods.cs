using System;
using System.Runtime.InteropServices;
using System.Text;

namespace KakaoMessageObserver.Infrastructure.Hook
{
    /// <summary>
    /// Win32 Native API Wrapper
    /// 
    /// - user32.dll 기능을 C#에서 사용하기 위한 Interop 클래스
    /// - Window 탐색 / Window 정보 조회 / Window 캡처 기능 제공
    /// 
    /// [주의]
    /// - 모든 함수는 OS Native 호출이므로 안정성 체크 필요
    /// - 잘못 사용 시 AccessViolation 발생 가능
    /// </summary>
    public static class NativeMethods
    {
        #region ===== Delegates =====

        /// <summary>
        /// Window Enum 콜백 델리게이트
        /// </summary>
        public delegate bool EnumWindowsProc(IntPtr hWnd, IntPtr lParam);

        #endregion


        #region ===== Structs =====

        /// <summary>
        /// Win32 RECT 구조체
        /// </summary>
        [StructLayout(LayoutKind.Sequential)]
        public struct RECT
        {
            public int Left;
            public int Top;
            public int Right;
            public int Bottom;

            public int Width => Right - Left;
            public int Height => Bottom - Top;
        }

        #endregion


        #region ===== Top-Level Window Enumeration =====

        /// <summary>
        /// 모든 Top-Level Window 순회
        /// </summary>
        [DllImport("user32.dll")]
        public static extern bool EnumWindows(
            EnumWindowsProc lpEnumFunc,
            IntPtr lParam);

        /// <summary>
        /// Window 제목 길이 가져오기
        /// </summary>
        [DllImport("user32.dll")]
        public static extern int GetWindowTextLength(IntPtr hWnd);

        /// <summary>
        /// Window 제목 문자열 가져오기
        /// </summary>
        [DllImport("user32.dll", CharSet = CharSet.Auto)]
        public static extern int GetWindowText(
            IntPtr hWnd,
            StringBuilder text,
            int count);

        /// <summary>
        /// Window가 Visible 상태인지 확인
        /// </summary>
        [DllImport("user32.dll")]
        public static extern bool IsWindowVisible(IntPtr hWnd);

        /// <summary>
        /// 부모 Window 핸들 가져오기
        /// </summary>
        [DllImport("user32.dll")]
        public static extern IntPtr GetParent(IntPtr hWnd);

        /// <summary>
        /// Window 클래스 이름 가져오기
        /// </summary>
        [DllImport("user32.dll", CharSet = CharSet.Auto)]
        public static extern int GetClassName(
            IntPtr hWnd,
            StringBuilder lpClassName,
            int nMaxCount);

        /// <summary>
        /// Window → Process ID 조회
        /// </summary>
        [DllImport("user32.dll")]
        public static extern uint GetWindowThreadProcessId(
            IntPtr hWnd,
            out uint processId);

        #endregion


        #region ===== Child Window Enumeration =====

        /// <summary>
        /// 자식 Window 순회
        /// </summary>
        [DllImport("user32.dll")]
        public static extern bool EnumChildWindows(
            IntPtr hWndParent,
            EnumWindowsProc lpEnumFunc,
            IntPtr lParam);

        /// <summary>
        /// 특정 Child Window 찾기
        /// </summary>
        [DllImport("user32.dll", CharSet = CharSet.Auto)]
        public static extern IntPtr FindWindowEx(
            IntPtr parentHandle,
            IntPtr childAfter,
            string? className,
            string? windowTitle);

        #endregion


        #region ===== Window Capture (OCR / Screenshot) =====

        /// <summary>
        /// Window 화면을 HDC로 출력 (비활성 창 캡처 가능)
        /// </summary>
        /// <param name="hwnd">Window Handle</param>
        /// <param name="hdcBlt">Target Device Context</param>
        /// <param name="nFlags">0 = 전체 Window</param>
        /// <returns>true 성공</returns>
        [DllImport("user32.dll")]
        [return: MarshalAs(UnmanagedType.Bool)]
        public static extern bool PrintWindow(
            IntPtr hwnd,
            IntPtr hdcBlt,
            uint nFlags);

        /// <summary>
        /// Window 화면 좌표 가져오기
        /// </summary>
        [DllImport("user32.dll", SetLastError = true)]
        public static extern bool GetWindowRect(
            IntPtr hwnd,
            out RECT lpRect);

        /// <summary>
        /// GDI Bit Block Transfer (화면 → Bitmap 복사)
        /// 
        /// - PrintWindow 실패 시 대체 캡처 방식
        /// - 화면 좌표 기준으로 픽셀 복사
        /// 
        /// [주의]
        /// - 최소한 Window가 화면에 보여야 정상 캡처됨
        /// </summary>
        [DllImport("gdi32.dll")]
        [return: MarshalAs(UnmanagedType.Bool)]
        public static extern bool BitBlt(
            IntPtr hdcDest,
            int nXDest,
            int nYDest,
            int nWidth,
            int nHeight,
            IntPtr hdcSrc,
            int nXSrc,
            int nYSrc,
            int dwRop);

        /// <summary>
        /// BitBlt Raster Operation Code
        /// 
        /// - SRCCOPY = Source 그대로 복사
        /// </summary>
        public const int SRCCOPY = 0x00CC0020;

        #endregion
    }
}
