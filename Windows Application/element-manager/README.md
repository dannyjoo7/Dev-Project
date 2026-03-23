# Element Manager

태그 기반 요소 관리 및 사용자 권한 시스템을 갖춘 Windows 데스크탑 애플리케이션입니다.

## 주요 기능
- 로그인 / 회원가입
- 요소(Element) 검색 및 상세 조회
- 태그 기반 분류 시스템
- 사용자 프로필 관리 (정보 수정, 탈퇴)
- 역할 기반 접근 제어 (일반 사용자 / 운영자 / 관리자)
- 운영자 권한 승인 관리

## 기술 스택
- C#, .NET 6.0, Windows Forms
- MySQL (MySql.Data)
- Syncfusion Grid/Tools
- FontAwesome.Sharp

## 코드 구조
```
element-manager/
├── Program.cs              # 진입점
├── Form1.cs                # 로그인
├── Form2.cs                # 회원가입
├── Form3.cs (Main)         # 메인 화면
├── Search.cs               # 요소 검색
├── SearchElement.cs        # 요소 카드 컨트롤
├── Element.cs              # 요소 상세 조회
├── AddTag.cs               # 태그 관리
├── UserUpdate.cs           # 사용자 정보 수정
├── UserPermit.cs           # 운영자 승인 관리
├── UserList.cs             # 사용자 목록 컨트롤
├── UserResign.cs           # 회원 탈퇴
├── contactUs.cs            # 문의하기
└── SQL Files/              # DDL 및 더미 데이터
```

## 멤버 구성

| 이름 | 역할 | GitHub |
|------|------|--------|
| 주찬영 | 메인 화면, 검색 화면, 회원 정보/탈퇴, 태그 관리, UI 설계 | [dannyjoo7](https://github.com/dannyjoo7) |
| socra167 | Element 검색 DB 연동, 운용자 승인 UI, DB 설계(DDL/더미데이터), Contact Us | [socra167](https://github.com/socra167) |
| worrysjh | 로그인/회원가입 구현, 운용자 승인 기능 | [worrysjh](https://github.com/worrysjh) |

## 빌드 및 실행

```bash
dotnet build
dotnet run
```

## 요구사항
- .NET 6.0
- MySQL Server
- Windows
