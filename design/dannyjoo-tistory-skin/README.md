<div align="center">

# dannyjoo-tistory-skin

개인 티스토리 블로그 스킨

[![Live](https://img.shields.io/badge/Live-dannyjoo.tistory.com-000000?style=for-the-badge&logo=google-chrome&logoColor=white)](https://dannyjoo.tistory.com)
[![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)](LICENSE)

</div>

---

## 미리보기

| 라이트 모드 | 다크 모드 |
|:---:|:---:|
| ![Light Mode](https://via.placeholder.com/480x300?text=Light+Mode) | ![Dark Mode](https://via.placeholder.com/480x300?text=Dark+Mode) |

> 스크린샷은 플레이스홀더입니다. 실제 캡처로 교체해주세요.

---

## 주요 특징

| 기능 | 설명 |
|:---|:---|
| 다크 모드 | 시스템 설정 자동 감지 + 수동 토글 |
| 반응형 레이아웃 | 데스크톱 2열, 모바일 1열 |
| 사이드바 | 프로필, 목차, 아카이브, 최근 글 |
| 읽기 진행률 바 | 상단 스크롤 진행률 표시 |

---

## 기능 목록

<details>
<summary><strong>레이아웃 & 네비게이션</strong></summary>

- 랜딩 페이지
- 반응형 글 목록 그리드
- 히어로 카드
- Archive 드롭다운
- Info 드롭다운
- 모바일 햄버거 메뉴
- 검색 오버레이 (`Ctrl+K`)

</details>

<details>
<summary><strong>글 상세 페이지</strong></summary>

- 사이드바 (프로필, 목차, 아카이브, 최근 글)
- 읽기 진행률 바
- 글자 크기 조절
- 이미지 라이트박스
- 코드 블록 복사 버튼
- 공유 버튼 (링크 복사, X)
- 관련 글 추천
- 이전 / 다음 글 네비게이션
- 댓글

</details>

<details>
<summary><strong>테마 & UX</strong></summary>

- 다크 모드
- 글래스모피즘 네비게이션 바
- 맨 위로 가기 버튼
- 스켈레톤 로딩
- 카테고리 / 최근 글 캐싱

</details>

---

## 설치

```
1. 티스토리 관리자 > 꾸미기 > 스킨 편집 > html 편집
2. skin.html과 style.css 내용을 각각 붙여넣기
3. 파일 업로드 탭에서 필요한 이미지 업로드
4. 적용 클릭
```

## 커스터마이징

**프로필** — `skin.html`에서 검색 후 수정:

| 찾기 | 변경 |
|:---|:---|
| `dannyjoo.io` | 브랜드명 |
| `dannyjoo` | 표시 이름 |
| 프로필 이미지 URL | 본인 이미지 URL |
| GitHub / YouTube 링크 | 본인 링크 |

**색상** — `style.css` 상단의 CSS Custom Properties 수정

---

## 파일 구조

```
dannyjoo-tistory-skin/
├── skin.html      # HTML 템플릿 + JavaScript
├── style.css      # 스타일시트
└── index.xml      # 스킨 메타데이터
```

## 기술 스택

`Vanilla JS` · `CSS Custom Properties` · `CSS Grid / Flexbox` · `Inter` · `Tistory Template Engine`

---

## 라이선스

[MIT](LICENSE)
