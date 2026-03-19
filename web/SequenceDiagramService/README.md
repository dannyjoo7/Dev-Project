# 🧩 시퀀스 다이어그램 생성기

웹 기반 **시퀀스 다이어그램 생성 도구**입니다.  
Figma 디자인을 기반으로 제작되었으며, 참여자와 메시지를 동적으로 추가·편집하여 시퀀스 다이어그램을 생성할 수 있습니다.

🔗 링크
https://sequence-diagram-service.vercel.app/

---

## ✨ 주요 기능

### 👤 참여자 관리
- 참여자 추가 / 삭제
- 참여자 순서 변경

### 💬 메시지 관리
- 호출 메시지
- 반환 메시지
- 동기 메시지
- 비동기 메시지
- 셀프 호출
- 메시지 추가 / 삭제
- 메시지 순서 변경

### 💾 데이터 관리
- JSON 파일로 저장
- JSON 파일 불러오기

### 🖼️ 이미지 내보내기
- SVG 다운로드
- PNG 다운로드
- PPT 및 문서 작업 활용 가능

---

## 📦 프로젝트 구조

~~~
├── guidelines/                # 설계 가이드 문서
├── src/
│   ├── app/
│   │   ├── components/
│   │   │   ├── figma/         # 피그마 기반 컴포넌트
│   │   │   ├── ui/            # 공통 UI 컴포넌트
│   │   │   ├── constants.ts   # 상수 정의
│   │   │   ├── MessageEditor.tsx      # 메시지 편집기
│   │   │   ├── ParticipantEditor.tsx  # 참여자 편집기
│   │   │   └── SequenceDiagram.tsx    # 시퀀스 다이어그램 렌더링
│   │   └── App.tsx            # 루트 애플리케이션 컴포넌트
│   ├── styles/
│   │   ├── fonts.css          # 폰트 설정
│   │   ├── index.css          # 전역 스타일
│   │   ├── tailwind.css       # Tailwind 설정
│   │   └── theme.css          # 테마 스타일
├── ATTRIBUTIONS.md            # 라이선스 및 출처
├── package.json               # 프로젝트 설정
├── postcss.config.mjs         # PostCSS 설정
└── vite.config.ts             # Vite 설정
~~~

---

## 🧱 기술 스택

- Vite 기반 프론트엔드 환경
- React + TypeScript
- Tailwind CSS
- JSON 기반 데이터 구조
- SVG / PNG 이미지 내보내기 지원