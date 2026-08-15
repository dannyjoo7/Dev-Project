# Grain

얇은 선과 여백 · 미리보기 [../README.md](../README.md) · 디자인 [design.md](design.md)

## 폴더

```
dist/          ★ 티스토리에 올리는 것. 이 안의 7개를 통째로 선택하면 된다
  index.xml  skin.html  style.css
  preview.gif  preview256.jpg  preview560.jpg  preview1600.jpg

build-css.py            mockup/blog-skin.css + fmt.css  ->  dist/style.css
preview.py              dist/skin.html 을 진짜 데이터로 채워 preview/ 에 렌더
make-preview-images.py  렌더한 홈 화면으로 dist/preview* 4종을 뽑는다
mockup/                 시안 원본. 새로 받으면 여기만 갈아끼운다
preview/                렌더 결과. 올리지 않는다
```

**`dist/style.css` 를 직접 고치지 말 것.** 생성물이라 다음 빌드에 날아간다.
고칠 곳은 `build-css.py` 의 `SKIN_ONLY` / `FMT_TWEAKS` 다.
`skin.html` 과 `index.xml` 은 손으로 고치는 파일이라 `dist/` 에 그대로 둔다.

## 올리기

```bash
python build-css.py && python preview.py && python make-preview-images.py
```

```
1. 지금 올라가 있는 스킨 백업 (되돌릴 유일한 방법)
2. 스킨 등록 > 추가 에서 dist/ 안의 7개를 전부 선택 > 저장
3. ★ 스킨 변경 에서 적용까지 누른다
4. 미리보기에서 목록에 카드가 나오는지 확인
```

**3번을 빼먹으면 아무것도 안 바뀐다.** 스킨 등록은 보관함에 넣는 것이고,
블로그가 읽는 건 적용할 때 뜨는 복사본이다. 등록만 다시 해도 그 복사본은 그대로다.
고칠 때마다 두 단계를 도는 게 번거로우면 `스킨 편집` 의 HTML/CSS 탭에 붙여넣는 쪽이 한 번에 끝난다.

저장이 걸렸는지는 이걸로 본다. 값이 바뀌면 걸린 것이다.

```bash
curl -s https://dannyjoo.tistory.com/ | grep -o 'skin/style.css?_version_=[0-9]*'
```

목록을 `<s_list>` 로 그림. Glass 는 `<s_article_rep>` 이라 이 블로그에서 처음 쓰는 태그임
4번에서 카드가 안 나오면 백업으로 되돌림

## 관리 설정

| 설정 | 값 | 안 맞으면 |
|:---|:---|:---|
| 글 목록 개수 | `10` | 페이지 수 표시가 틀림 |
| 카테고리 글 수 표시 | 켜기 | 사이드바 숫자 안 나옴 (통계는 `sitemap.xml` 로 폴백) |

## 커스터마이징

**숫자**
`skin.html` 위쪽 상수 수정

| 찾기 | 변경 |
|:---|:---|
| `START` | 가장 오래된 글 발행일. 운영 일수 기준 |
| `PER_PAGE` | 관리 > 블로그 의 글 목록 개수 |

글 수 · 카테고리 수 · 페이지 수 · 마지막 글 · 운영 일수 자동 적용

**문구**
`skin.html` 에서 검색 후 수정

| 찾기 | 변경 |
|:---|:---|
| `hero__title` | 홈 큰 제목 |
| `hero__lede` | 홈 소개 문장 |
| `tag-list` | 관심사 태그 |
| `link-list` | 외부 링크 |
| `profile.png` | 프로필 이미지 주소 |

**한글 라벨**
작은 라벨은 모노 + 대문자 + 자간이 넓어 한글이 벌어짐.`label-ko` 를 같이 붙임

```html
<p class="widget__title label-ko">이 글의 목차</p>
<p class="widget__title">Categories</p>
```

## 안 한 것

공지 화면 (`<s_notice_rep>` 없음. 공지를 쓰면 안 보임)
보호글 비밀번호 화면
홈 커버 (관리에서 켜면 홈이 달라질 수 있음)
