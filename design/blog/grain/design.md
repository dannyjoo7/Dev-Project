# Grain: 디자인

dannyjoo.tistory.com 의 두 번째 스킨. 잇길 디자인 시스템을 블루 팔레트로 옮겼다.
원칙은 하나다. **채워진 카드 대신 얇은 선과 여백.** 위계는 크기와 굵기로만 준다.

적용하는 방법은 [README.md](README.md) 에 있다. 이 문서는 왜 그렇게 생겼는지를 적는다.

---

# 1부. 브리프

Claude Design 에 넣는 원문이다. 시안을 다시 받을 때도 이걸 그대로 쓴다.

한국어 기술 블로그(dannyjoo.io) 스킨을 만든다. 글쓴이는 안드로이드·웹 개발자다.
화면 3종: **홈(소개 랜딩)**, **글 목록**, **글 상세**.

---

## 1. 색

잇길 디자인 시스템을 따르되 **색만 블루 계열로 바꾼다.**
잇길은 로즈(`#e4607f`) 기반인데 이 블로그는 블루를 쓴다.
밝기·채도 관계는 유지하고 색상만 옮긴 매핑이다.

### 강조색

| 역할 | 잇길(로즈) | 새 값(블루) |
|---|---|---|
| 강조 | `#e4607f` | **`#0058bc`** |
| 강조 진한 (hover·강조 텍스트) | `#a8365a` | `#00408c` |
| 강조 배경 (연한 채움) | `#fceaee` | `#eaf2fc` |
| 연한 선 | `#f3cbd6` | `#cbdef3` |
| 연한 선 2 | `#f1c4d0` | `#c4d9f1` |

### 중립색

따뜻한 회색을 차가운 회색으로. 오른쪽이 이 블로그가 지금 쓰는 값이다.

| 역할 | 잇길(따뜻) | 새 값(차가움) |
|---|---|---|
| 배경 | `#fbf4f3` | `#f9f9fb` |
| 카드 | `#ffffff` | `#ffffff` |
| 본문 글자 | `#181215` | `#1a1c1d` |
| 보조 글자 | `#6b5a5f` | `#414755` |
| 흐린 글자 | `#a29095` | `#5f6577` |
| 선 | `#efe2e2` | `#e2e2e4` |

> 흐린 글자를 그대로 옮기면 `#717786` 인데 배경 대비 4.3:1 이라 작은 글씨에 못 쓴다.
> `#5f6577` 로 어둡게 잡아 4.5:1 을 넘겼다.

대비 확인 완료: 본문 16.3:1 / 보조 8.8:1 / 강조 6.4:1 / 흰 글씨 on 강조버튼 6.7:1

---

## 2. 모양: 애플식 라운디드 스퀘어

**칩·태그·버튼·썸네일 모두 알약(pill)이 아니라 모서리를 둥글린 사각형으로 만든다.**

- `border-radius: 999px` 같은 완전 둥근 형태 **금지**
- 크기별로 반경을 일정한 비율로: 작은 칩 `8px`, 버튼·태그 `10px`, 카드·썸네일 `14~16px`, 큰 패널 `20px`
- 애플 아이콘처럼 **모서리가 부드럽게 이어지는 느낌**(슈퍼타원)을 목표로 한다.
  `border-radius` 만으로 어색하면 살짝 큰 반경 + 얇은 테두리로 보정한다
- 테두리는 얇게(`1px`), 그림자는 최소한으로. 잇길처럼 **선으로 구분**하는 쪽

---

## 3. 하지 말 것 (AI 티 나는 패턴)

아래는 전부 피한다. 이런 게 하나라도 들어가면 다시 만든다.

- 보라~파랑 **그라데이션 배경**, mesh/blob 배경 장식
- 유리모피즘(blur 카드) **남발**
- 제목이나 라벨에 붙는 **이모지 아이콘** (✨ 🚀 💡 …)
- 완전히 둥근 알약 버튼·태그
- 카드마다 큰 드롭섀도, 과한 hover 확대(1.05 이상)
- 의미 없는 큰 여백만으로 채운 히어로
- 모든 섹션에 같은 카드 그리드를 반복하는 구성
- 형광색 강조, 네온 글로우

**대신** 얇은 선, 절제된 여백, 정보 밀도가 있는 개발 블로그의 톤으로 간다.

---

## 4. 참고할 성격: 개발 블로그다운 밀도

`inpa.tistory.com` 같은 한국 개발 블로그의 **구조적 성격**을 참고한다. 베끼지는 않는다.

- **좌측 사이드바에 카테고리 트리** + 각 항목에 글 수 배지
- **상단 메가 메뉴**: 드롭다운이 넓게 열리고 여러 열로 항목이 정렬됨
  (언어 / 라이브러리 / 프레임워크 같은 그룹)
- 목록·본문 모두 **정보가 빽빽하되 답답하지 않게**. 여백으로 도망가지 않는다
- 글 수, 날짜, 카테고리 같은 메타 정보를 숨기지 않고 보여준다

---

## 5. 코드 블록: 맥 창 스타일

본문 코드 블록을 **macOS 창처럼** 만든다.

- 상단에 타이틀바, 왼쪽에 **신호등 점 3개**(빨강·노랑·초록)
- 타이틀바 가운데나 왼쪽에 **파일명 또는 언어 이름**
- 오른쪽 끝에 **복사 버튼**
- 본체는 어두운 배경 + 문법 강조. **라이트 모드에서도 코드 블록은 어둡게 유지**
- 모서리는 위 라운디드 스퀘어 규칙(`14px` 정도)
- 가로로 긴 줄은 잘리지 말고 **블록 안에서 가로 스크롤**

언어: bash, kotlin, plain 이 자주 나온다.

---

## 6. 글 상세: 여기가 제일 중요하다

**세 화면 중 이걸 제일 공들여 만든다.** 목록은 스쳐 지나가는 곳이고, 사람이 시간을 쓰는 건 본문이다.
읽는 맛이 안 나면 나머지가 아무리 예뻐도 소용없다.

### 실제 글에 뭐가 들어오나

이 블로그 글은 늘 아래 요소로 짜인다. **전부 스타일을 정의해야 한다.**

| 요소 | 실제 모습 |
|---|---|
| 번호 붙은 h2 | `0. 개요`, `1. Now Bar`, `2. 구현` … 글마다 5~7개 |
| h3 | h2 아래 소제목 |
| 본문 문단 | 한국어. 굵은 강조와 인라인 코드가 섞인다 |
| 회색 정보 박스 | 번호 목록 2~4줄이 들어간다. 반투명 회색 |
| 파란 강조 박스 | `📌` + 한 줄 결론 + 부연. 글마다 1~3개 |
| 코드 블록 | bash / kotlin / plain. 가로로 긴 줄 잦음 |
| 표 | 3~4열 비교표. 글마다 1~2개 |
| 이미지 + 캡션 | 캡션이 이미지 **위**에 작게 붙는다 |
| 인용 | 짧은 한 줄 인용 |
| 구분선 | 장과 장 사이 |
| References | 맨 아래, 링크 목록 |

### 읽기

- 본문 폭 `52rem` 안팎, 글자 `16~17px`, 줄간격 `1.7~1.8`
- **문단 사이 간격이 줄간격보다 확실히 커야** 문단이 덩어리로 읽힌다
- h2 위 여백은 아래 여백의 2배 이상. 장이 바뀌는 게 눈에 보이게
- h2 에 번호가 이미 들어 있으니 **장식 번호를 또 붙이지 않는다**
- 인라인 코드는 배경을 아주 옅게. 본문 흐름을 끊지 않을 정도로만

### 목차: 두 군데 있다

**(1) 본문 맨 위 목차 박스**

- 글 시작을 가로막지 않게 **납작하게**. 높이를 잡아먹으면 안 된다
- 항목은 얇은 선으로만 구분. 카드나 알약으로 감싸지 않는다
- 번호는 글에 이미 있으니 그대로 두고, 목차가 번호를 새로 매기지 않는다

**(2) 사이드바 목차**

- 스크롤을 따라 **현재 보고 있는 장을 표시**한다
- 왼쪽에 얇은 세로선을 두고, 현재 항목 위치에만 강조색 마커
- h3 는 h2 아래로 들여쓴다
- 항목마다 배경을 칠하거나 박스를 씌우지 않는다. **선과 글자 굵기로만** 구분

### 박스 두 종류

회색 정보 박스와 파란 강조 박스는 **역할이 다르니 생김새도 달라야** 한다.
다만 둘 다 **반투명**으로 만들어 라이트·다크 양쪽에 묻게 한다.

- 회색: 배경 `rgba(128,128,128,0.14)` + 테두리 `rgba(128,128,128,0.45)`
- 파랑: 배경 `rgba(0,88,188,0.12)` + 테두리 `rgba(0,88,188,0.5)`
- 안에 번호 목록이 들어가므로 줄 간격과 들여쓰기를 미리 잡아둔다

### 이미지

- 캡션은 이미지 **위**, 작고 흐린 글씨
- 세로로 긴 폰 캡처가 자주 온다. 높이 상한 `70vh`, 비율 유지, 가운데 정렬
- 가로로 긴 다이어그램은 본문 폭을 살짝 넘겨 시원하게 보여줘도 된다

### 표

- 3~4열 비교표. 헤더 배경은 아주 옅게, 나머지는 선으로만
- 좁은 화면에서 **표 안에서 가로 스크롤**. 글자를 줄이거나 줄바꿈으로 뭉개지 않는다

### 글 주변

제목·카테고리·날짜 / 본문 / 태그 / 이전·다음 글 / 댓글 순서.
상단에 읽기 진행률 바. 이것들이 본문보다 튀면 안 된다.

### 이 화면에서 특히 하지 말 것

- 본문을 **카드 안에 넣고 그림자**를 주는 것. 본문은 배경 위에 그냥 놓인다
- 장마다 구분선을 그어 문서를 토막 내는 것
- 소제목 옆 아이콘, 문단 앞 불릿 아이콘
- 인용문에 큰 따옴표 장식 이미지
- 목차·태그·날짜를 전부 알약 배지로 만드는 것

---

## 7. 애니메이션: 잇길에서 그대로 가져온다

### 이징 토큰

```css
--ease-out:    cubic-bezier(0.22, 0.9, 0.24, 1);
--ease-spring: cubic-bezier(0.34, 1.4, 0.64, 1);
```

### 기본 규칙

- 잇길의 기본 트랜지션은 **`transform 0.14s var(--ease-out)`** 이다. 이걸 기준으로 삼는다
- 색·배경은 `0.2s ease`, 글자색은 `0.24s ease`
- **짧고 절제되게.** 0.3s 를 넘기지 않는다
- 호버는 **transform 위주**. 크기 변화는 `translateY(-2px)` 나 `scale(1.01)` 정도까지만

### 가져올 키프레임

```css
/* 제목이 아래에서 드러나며 올라옴 — 잇길 titleRise */
@keyframes titleRise {
  from { clip-path: inset(-8% 0 100% 0); transform: translateY(12px); }
  to   { clip-path: inset(-8% 0 -20% 0); transform: none; }
}

/* 카드·본문 진입 — 잇길 popIn */
@keyframes popIn {
  from { opacity: 0; transform: translateY(10px) scale(0.985); }
  to   { opacity: 1; transform: none; }
}

@keyframes fadeIn { from { opacity: 0 } to { opacity: 1 } }
```

### 어디에 쓰나

- **글 제목·히어로 문구**: `titleRise`
- **목록 카드**: `popIn`, 순서대로 아주 짧은 지연(40ms 간격)
- **본문 진입**: `fadeIn`
- **카드 호버**: `translateY(-2px)` + 테두리 색 진해짐, `0.14s`
- **링크 호버**: 밑줄이 좌→우로 그어지는 형태
- **드롭다운·시트**: 아래에서 살짝 올라오며 페이드
- `prefers-reduced-motion` 이 켜져 있으면 전부 끈다

### 로딩도 잇길 것을 그대로 쓴다

잇길은 로딩을 **스피너로 때우지 않는다.** 선이 그어지고 점이 하나씩 켜지는 마크와,
숨쉬듯 밝기가 오르내리는 스켈레톤 두 가지를 쓴다. 그대로 가져온다.

```css
/* 로딩 마크 — 선이 그어진 뒤 점 3개가 차례로 켜진다 */
@keyframes markLine {
  0%       { stroke-dashoffset: 1; opacity: .55; }
  84%      { stroke-dashoffset: 0; opacity: .55; }
  92%, 100%{ stroke-dashoffset: 0; opacity: 0; }
}
@keyframes markDot1 { 0% {opacity:0; transform:scale(.3)} 10%,92% {opacity:1; transform:scale(1)} 100% {opacity:0; transform:scale(.3)} }
@keyframes markDot2 { 0%,34% {opacity:0; transform:scale(.3)} 46%,92% {opacity:1; transform:scale(1)} 100% {opacity:0} }
@keyframes markDot3 { 0%,66% {opacity:0; transform:scale(.3)} 78%,92% {opacity:1; transform:scale(1)} 100% {opacity:0} }
/* 선: markLine 1.9s var(--ease-out) infinite
   점: 각 markDot1~3 1.9s var(--ease-spring) infinite */

/* 스켈레톤 — 숨쉬는 밝기 + 좌→우로 훑고 지나가는 자국 */
@keyframes skBreathe { 0%,100% { opacity:.6 } 50% { opacity:1 } }
@keyframes traceR {
  0%  { clip-path: inset(0 100% 0 0); opacity: 1; }
  44% { clip-path: inset(0 0 0 0);    opacity: 1; }
  84%,100% { clip-path: inset(0 0 0 0); opacity: 0; }
}
/* skBreathe 1600ms ease-in-out infinite
   traceR   2100ms linear infinite both */

/* 실제 내용이 들어올 때 — 깜빡임 방지용 지연 페이드 */
@keyframes skelIn { to { opacity: 1 } }
/* skelIn 0.26s var(--ease-out) 0.25s forwards */
```

**쓰는 곳**

- 목록·사이드바(아카이브, 최근 글)가 채워지기 전 → **스켈레톤**(`skBreathe` + `traceR`)
- 페이지 전환이나 검색 결과 대기 → **로딩 마크**
- 내용이 도착하면 `skelIn` 으로 0.25s 지연 후 페이드. 바로 튀어나오지 않게

> 스켈레톤은 **실제 들어올 내용과 같은 크기·간격**으로 만든다. 자리가 밀리면 안 된다.
> 그리고 내용이 들어오면 **반드시 스켈레톤을 지운다.** 남아서 같이 보이면 안 된다.

---

## 8. 다크모드는 필수

라이트/다크 양쪽을 다 설계한다. 선택 사항이 아니다.
이 블로그는 다크모드에서 글씨가 사라지는 사고를 여러 번 겪었다.

- 색은 **전부 CSS 변수**로 뽑고, 다크는 변수만 교체
- 본문 요소에 색을 직접 박지 않는다
- 박스·구분선은 불투명 회색 대신 **반투명**(`rgba(128,128,128,α)`)으로
- 다크에서도 본문 7:1, 강조 4.5:1 을 맞춘다

---

## 9. 하드 제약

경험에서 나온 것들이라 반드시 지킨다.

1. **세로로 긴 이미지가 화면을 잡아먹지 않게.** 폰 캡처(1248×1972 같은)가 자주 들어온다.
   지금 스킨은 이미지를 무조건 본문 폭까지 늘려서 높이 1365px 로 나왔다.
   높이 상한(`max-height: 70vh`)을 두고 비율은 유지한다
2. **목록 화면에 본문을 싣지 않는다.** 지금은 목록에서 글 10개의 본문을 전부 받아
   JS 로 숨긴다. 339KB 중 284KB 가 숨길 내용이었다
3. **JS 로 보였다 숨겼다 하지 않는다.** 서버가 내려준 HTML 이 그대로 보여야 한다
4. **목록 하단에 페이지 이동 UI**(이전/다음 + 페이지 번호)
5. **터치 타깃 44px 이상**, 모바일 1열

---

## 10. 화면별 필수 요소

**공통 상단**. 브랜드명, Info 드롭다운(소개·링크), Archive 메가 드롭다운(카테고리),
검색, 다크모드 토글, 모바일 햄버거

**홈**. 히어로 문구, 프로필, 관심사, 링크, "글 보러 가기" CTA

**목록**. 카드마다 썸네일·카테고리·제목·요약·날짜.
데스크톱 2열, 모바일 1열. 첫 글을 크게 보여주는 히어로 카드는 선택. 하단 페이지 이동

**상세**. 카테고리, 제목, 날짜, 본문, 태그, 이전/다음 글, 댓글.
좌측 사이드바에 프로필·목차·아카이브·최근 글. 상단에 읽기 진행률 바

---

## 11. 산출물

- **단일 HTML + CSS**, 프레임워크 없이
- 색·간격·타이포·모션을 **전부 CSS 변수**로. 다크는 변수 교체만으로
- 화면 3종을 각각 볼 수 있게
- 클래스 이름은 의미가 드러나게 (`article-card__title` 식)

티스토리 전용 태그(`[##_article_rep_title_##]` 등)는 넣지 않아도 된다. 나중에 심는다.


---

# 2부. 실제 데이터에 연결

히어로의 "글 128편", 목록 헤더의 "전체 128편 · 6개 카테고리 · 13페이지",
사이드바 카테고리 트리를 전부 **`[##_category_##]` 하나로** 채운다.

근거는 [inpa.tistory.com](https://inpa.tistory.com/) 이 실제로 쓰는 방식이다.

```js
// inpa 스킨 원본
document.getElementById('home-postings').dataset['to'] =
  document.querySelector('.link_tit > .c_cnt').textContent.match(/[0-9]+/)[0];
```

## 티스토리가 뱉는 마크업

`[##_category_##]` 의 출력은 고쳐 쓸 수 없는 고정 구조다.

```html
<ul class="tt_category">
  <li class=""><a href="/category" class="link_tit"> 분류 전체보기 <span class="c_cnt">(872)</span> </a>
    <ul class="category_list">
      <li class=""><a href="/category/Skill" class="link_item"> Skill <span class="c_cnt">(16)</span> </a>
        <ul class="sub_category_list">
          <li class="selected"><a href="/category/Skill/Web" class="link_sub_item"> Web <span class="c_cnt">(12)</span> </a></li>
        </ul>
      </li>
    </ul>
  </li>
</ul>
```

알아둘 것 세 가지.

1. 전부 **"분류 전체보기" `<li>` 안에** 들어간다. 대분류 목록이 그 자식이다.
2. 현재 보고 있는 카테고리의 `<li>` 에만 `class="selected"` 가 붙는다. **부모에는 안 붙는다.**
3. 글 수 `(872)` 는 **괄호까지 텍스트**다. 숫자만 쓰려면 JS 로 벗겨야 한다.

### ★ 글 수는 블로그마다 켜고 끈다

같은 티스토리인데 [gurtn.tistory.com](https://gurtn.tistory.com/) 은 `c_cnt` 자체가 없다.

```html
inpa    <a href="/category" class="link_tit"> 분류 전체보기 <span class="c_cnt">(872)</span> </a>
gurtn   <a href="/category" class="link_tit"> 분류 전체보기 </a>
```

그래서 아래 JS 에 **`sitemap.xml` 폴백**을 넣어 뒀다. 꺼져 있어도 숫자는 나온다.

## 1. 마크업

사이드바 카테고리 위젯의 목업 트리를 통째로 치환자로 바꾼다.

```html
<div class="widget">
  <p class="widget__title">Categories</p>
  <nav class="widget__body cat-tree">
    <s_sidebar_element>[##_category_##]</s_sidebar_element>
  </nav>
</div>
```

숫자가 들어갈 자리에는 훅을 단다. **안쪽 글자는 지우지 말고 최근 값을 그대로 둔다.**
JS 가 못 돌아도 살짝 오래된 숫자가 보이는 게 `…` 보다 낫다.

```html
<!-- 히어로 -->
<span>글<b data-stat="posts">14편</b></span>
<span>마지막 글<b data-stat="last">2026.08.13</b></span>
<span>기록<b data-stat="days">1,186일째</b></span>

<!-- 목록 헤더 -->
<p class="list-head__count">
  전체 <span data-stat="posts">14편</span> ·
  <span data-stat="cats">4개</span> 카테고리 ·
  <span data-stat="pages">2페이지</span>
</p>
```

`지금 하는 일` 과 `시작` 은 티스토리에 그런 데이터가 없다. 직접 적는다. inpa 도 그렇게 한다.

## 2. CSS

`.cat-tree` 안에서만 먹도록 가둔다. 목업의 `.cat-tree__*` 규칙은 지워도 되고,
스켈레톤용으로 남겨도 상관없다.

**선택자 특이도를 맞춰서 쓴다.** 초기화에 `ul` 같은 타입 선택자를 섞으면
`.cat-tree .tt_category ul`(0,2,1)이 `.cat-tree .sub_category_list`(0,2,0)를 이겨서
**들여쓰기와 하위 글자 크기가 통째로 죽는다.** 실제로 처음에 그렇게 짰다가 걸렸다.
그래서 아래는 하위 규칙을 전부 `.cat-tree .tt_category` 로 한 단계 더 받쳐 놨다.

```css
/* 티스토리 [##_category_##] 출력을 cat-tree 모양으로.
   마크업을 못 고치니 선택자로 맞춘다 */
.cat-tree .tt_category,
.cat-tree .tt_category ul {
  display: flex;
  flex-direction: column;
  margin: 0;
  list-style: none;
}
/* padding 초기화는 하위 목록을 빼고 건다 (들여쓰기를 지우지 않게) */
.cat-tree .tt_category,
.cat-tree .tt_category .category_list { padding: 0; }

.cat-tree .tt_category a {
  min-height: 40px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: var(--sp-2);
  font-size: var(--fs-sm);
  font-weight: 700;
  letter-spacing: -0.02em;
  color: var(--ink-2);
  transition: color var(--t-ink) ease;
}
.cat-tree .tt_category a:hover { color: var(--ink); }

/* 하위 카테고리 — 들여쓰기 + 왼쪽 선 */
.cat-tree .tt_category .sub_category_list {
  padding-left: var(--sp-3);
  margin: 2px 0 var(--sp-2);
  border-left: 1px solid var(--ln);
}
.cat-tree .tt_category .link_sub_item {
  min-height: 34px;
  font-weight: 600;
  font-size: var(--fs-xs);
  color: var(--ink-3);
}
.cat-tree .tt_category .link_sub_item:hover { color: var(--ink-2); }

/* 현재 카테고리 */
.cat-tree .tt_category .selected > a { color: var(--acc-d); font-weight: 800; }

.cat-tree .tt_category .c_cnt {
  font: 600 var(--fs-xs)/1 var(--ff-mono);
  color: var(--ink-3);
  flex: none;
}
```

## 3. JS

스킨 맨 아래, `</body>` 앞에 둔다.

```html
<script>
(function () {
  // 관리 > 블로그 의 "글 목록 개수". 목록을 훑어 10 으로 확인했다
  var PER_PAGE = 10;

  var tree = document.querySelector('.cat-tree .tt_category');

  // (872) → 872
  if (tree) {
    Array.prototype.forEach.call(tree.querySelectorAll('.c_cnt'), function (el) {
      var n = el.textContent.match(/\d+/);
      el.textContent = n ? n[0] : '';
    });
  }

  function put(key, text) {
    Array.prototype.forEach.call(
      document.querySelectorAll('[data-stat="' + key + '"]'),
      function (el) { el.textContent = text; }
    );
  }

  function paint(posts, cats) {
    if (posts) {
      put('posts', posts + '편');
      put('pages', Math.ceil(posts / PER_PAGE) + '페이지');
    }
    if (cats) put('cats', cats + '개');
  }

  // 운영 일수 — 시작일만 박아두면 매일 저절로 올라간다 (inpa 도 같은 방식)
  var START = '2023/05/16';   // 가장 오래된 공개 글(/4)
  var days = Math.floor((Date.now() - new Date(START).getTime()) / 86400000);
  put('days', days.toLocaleString() + '일째');

  // 마지막 글 — 목록 첫 카드의 날짜를 그대로 쓴다. 추가 요청 없음
  var firstDate = document.querySelector('.article-card__meta, .recent-list__date');
  if (firstDate) {
    var d = firstDate.textContent.match(/\d{4}[.\-]\d{2}[.\-]\d{2}/);
    if (d) put('last', d[0].replace(/-/g, '.'));
  }

  var totalEl = tree && tree.querySelector('.link_tit .c_cnt');
  var total = totalEl ? parseInt(totalEl.textContent, 10) : 0;

  if (total) {
    // 글 수 표시가 켜져 있는 경우. 추가 요청 0회
    paint(total, tree.querySelectorAll('.category_list > li').length);
    return;
  }

  // 꺼져 있으면 sitemap 으로. 2.8KB 한 번
  fetch('/sitemap.xml')
    .then(function (r) { return r.text(); })
    .then(function (xml) {
      var doc = new DOMParser().parseFromString(xml, 'application/xml');
      var urls = Array.prototype.map.call(doc.querySelectorAll('loc'), function (n) {
        return n.textContent;
      });
      paint(
        urls.filter(function (u) { return /\/\d+$/.test(u); }).length,
        urls.filter(function (u) { return /\/category\/[^\/]+$/.test(u); }).length
      );
    })
    .catch(function () { /* 마크업에 적힌 값을 그대로 둔다 */ });
})();
</script>
```

## 4. 확인

### 로컬에서 (붙여넣기 전)

```bash
python 카테고리-검증.py
```

inpa 에서 받아온 **실제 티스토리 마크업**(`카테고리-마크업-샘플.html`)에
이 문서의 CSS·JS 를 그대로 발라 라이트·다크로 렌더한다.
문서에서 코드를 뽑아 쓰기 때문에 문서와 검증이 어긋나지 않는다.

확인하는 것: 괄호 제거, 하위 들여쓰기, 현재 카테고리 색, 글 수 정렬, 통계 훅,
그리고 `sitemap` 폴백까지.

### 스킨 편집기에서 (붙여넣은 뒤)

카테고리 위젯을 보면 바로 갈린다.

| 보이는 것 | 뜻 | 결과 |
|---|---|---|
| `Skill 12` 처럼 숫자가 붙음 | 글 수 표시 켜짐 | 요청 0회로 끝 |
| 이름만 나옴 | 꺼짐 | sitemap 폴백이 채움. 그래도 **사이드바 숫자는 안 나온다** |

숫자가 없으면 관리 화면에서 카테고리 글 수 표시를 켜는 게 낫다. 사이드바까지 한 번에 해결된다.

## 지금 블로그의 실제 값

목업의 128편·6개·13페이지는 가짜 숫자다. `/category?page=N` 을 끝까지 훑어 확인한 값.

| | 값 |
|---|---|
| 글 | **14편** (공개 기준) |
| 카테고리 | 4개 (Skill · 게임 · 리뷰 · 일상), 하위 포함 11개 |
| 페이지 | 2쪽 (1쪽 10편 + 2쪽 4편) |
| 마지막 글 | 2026.08.13 |
| 기록 | 1,186일째 (2023.05.16 부터) |

목업의 `시작 2019.03` 은 가짜다. 가장 오래된 공개 글 `/4` 의 발행일이 **2023-05-16** 이다.

```
<meta property="article:published_time" content="2023-05-16T14:14:50+09:00">
```

날짜를 그냥 적는 것보다 **일수로 두는 쪽이 낫다.** 하루에 하나씩 저절로 올라간다.
`START` 만 박아두면 되고, 그 뒤로는 손댈 일이 없다.

> 비공개인 `/1` 이 더 오래된 글일 수 있다. 열어볼 수 없어서 확인이 안 된다.
> 실제 시작일을 알면 `START` 를 그 날짜로 바꾼다.

### 글 관리는 17개인데 왜 14편인가

**비공개 3편이 빠진 값이다.** 없는 번호를 하나씩 두드려 봤다.

| 상태 | 글 번호 | 뜻 |
|---|---|---|
| 403 | 1 · 10 · 17 | 살아 있지만 **비공개**. 목록에도 안 나온다 |
| 404 | 2 · 3 · 5 · 13 · 14 · 15 | 삭제됨 |

14(공개) + 3(비공개) = 17(글 관리). 계산이 맞는다.

**화면에는 14 가 맞다.** 방문자가 실제로 열 수 있는 글이 14편이다.
보호글이었다면 목록에 자물쇠로 뜨니 세야 하지만, 이건 비공개라 아예 안 보인다.

`c_cnt` 도 방문자 기준이라 14 로 나올 것이다. 붙여넣고 한 번 확인한다.

### PER_PAGE 는 10 이 맞다

1쪽에 10편, 2쪽에 4편이 나왔다. 관리 설정을 바꾸지 않는 한 `PER_PAGE = 10` 그대로 둔다.
