# KakaoMessageObserver

간단한 카카오톡 채팅 모니터링 도구입니다. 화면에서 카카오톡 채팅창을 찾아 OCR로 메시지를 읽어오고, UI에 로그와 채팅방 목록을 표시합니다. WPF로 구현된 데스크탑 애플리케이션입니다.

## 주요 기능
- 활성화된 카카오톡 채팅창 탐지
- 채팅창 화면 OCR 분석으로 송수신 메시지 추출
- 중복 메시지 필터링 및 시간 기반 노이즈 제거
- 실행 상태(카카오 연결, 모니터링, DB 연결) 표시
- UI에 채팅방 목록 및 메시지 로그 표시

## 기술 스택
- UI: WPF (.NET 10)
- OCR Engine: Sdcb.PaddleOCR (PaddleOCR V4 기반)
- Image Processing: OpenCvSharp4

## 요구사항
- .NET 10
- Windows (WPF 기반)
- Visual Studio 2022/2023 권장
- (선택) OCR 관련 네이티브 라이브러리나 드라이버가 필요한 경우 별도 설치 필요

## 빌드 및 실행
1. 레포지토리를 클론합니다:
   `git clone https://github.com/dannyjoo7/KakaoMessageObserver.git`
2. 솔루션을 Visual Studio에서 엽니다.
3. 타겟 프레임워크가 `.NET 10`인지 확인 후 빌드합니다.
4. 디버그 또는 릴리스로 실행합니다.

CLI로 빌드/실행:

```
dotnet build
dotnet run --project KakaoMessageObserver.Presentation
```

## 설정 및 구성
- OCR 동작과 창 탐지 관련 구현체는 `Infrastructure/Automation` 폴더의 클래스들을 확인하세요 (`KakaoOcrReader`, `KakaoWindowFinder` 등).
- 외부 OCR 엔진을 사용할 경우 해당 DLL 또는 네이티브 종속성을 연결해야 합니다.
- DB 연결 등 추가 기능은 ViewModel과 서비스 인터페이스를 통해 확장 가능합니다.

## 사용법
- 프로그램 실행 후 UI에서 상태 표시를 확인하세요.
- 활성화된 카카오톡 창이 있으면 자동으로 채팅방을 탐지해 `ChatRoomList`에 추가합니다.
- OCR로 읽은 메시지는 `MessageLog`에 남고, 동일한 메시지는 중복 필터링됩니다.

## 코드 구조(간단 요약)
- `Presentation` — WPF UI 및 ViewModel (`MainWindow`, `MainStatusViewModel`)
- `Domain` — 인터페이스 정의 (`IKakaoWindowFinder`, `IKakaoMessageReader` 등)
- `Infrastructure` — 카카오 창 탐지 및 OCR 처리 구현 (`KakaoOcrReader`, `KakaoMessageReader`)

관심사 분리(창 탐지, OCR, UI)는 인터페이스 기반으로 구성되어 있어 테스트 및 구현 교체가 용이합니다.

## 기여
- 이슈(버그/기능 요청)를 GitHub 레포지토리에 남겨주세요.
- 변경은 포크 후 PR로 제출해 주세요. PR에 변경 내용과 테스트 방법을 적어 주시면 리뷰가 수월합니다.

## 라이선스
- 저장소에 `LICENSE` 파일이 있는 경우 해당 라이선스를 따릅니다. 별도 파일이 없으면 기본적으로 소유자에게 문의하세요.

---