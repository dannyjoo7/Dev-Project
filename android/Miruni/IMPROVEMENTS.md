# Miruni 프로젝트 개선사항

> 프로젝트 분석 일자: 2026-03-19
> 대상: com.joo.miruni (Clean Architecture + MVVM, Jetpack Compose, Room, Hilt)
> Flutter 마이그레이션 예정 → `FLUTTER_MIGRATION.md` 참고

---

## 미착수 항목

없음. 모든 개선사항 완료.

---

## 완료 항목 요약

| # | 항목 | 완료일 |
|---|------|--------|
| 1 | UnlockViewModel 무한 루프 수정 | 2026-03-19 |
| 2 | ForegroundService 리시버 중복 등록 수정 | 2026-03-19 |
| 3 | 테스트 코드 작성 (24개 파일, 70+ 테스트 케이스) | 2026-03-19 |
| 4 | 에러 처리 보강 (빈 onFailure 등) | 2026-03-19 |
| 5 | 코드 중복 제거 (DateTimeFormatUtil, TaskLoadingHelper) | 2026-03-19 |
| 6 | 하드코딩 문자열 → strings.xml + stringResource() 전환 | 2026-03-19 |
| 7 | ViewModel 비대화 해소 (포맷/로딩 로직 추출) | 2026-03-19 |
| 8 | LiveData → StateFlow 전체 마이그레이션 (9개 ViewModel) | 2026-03-19 |
| 9 | Navigation Compose 전환 + 미사용 Activity 삭제 | 2026-03-19 |
| 10 | 접근성 contentDescription 로컬라이징 | 2026-03-19 |
| 11 | BasicDialog/DurationUnit/BottomNav 문자열 리소스화 | 2026-03-19 |
| 12 | ForegroundService Intent 안전성 + 문자열 리소스화 | 2026-03-19 |
| 13 | 날짜 포맷 DateTimeFormatter + Locale 전환 | 2026-03-19 |
| 14 | 접근성 추가 (semantics, testTag) | 2026-03-19 |
| 15 | 다크 모드 커스터마이징 (values-night/colors.xml) | 2026-03-19 |
| 16 | UseCase 인터페이스/구현 패턴 간소화 (72 → 36 파일) | 2026-03-19 |
| 17 | ProGuard 규칙 추가 | 2026-03-19 |
| 18 | fallbackToDestructiveMigration 제거 | 2026-03-19 |
| 19 | 오타 수정 (UseCaseModule) | 2026-03-19 |
| 20 | TestUseCase 제거 | 2026-03-19 |
| 21 | SharedPreferences → DataStore 마이그레이션 | 2026-03-19 |
