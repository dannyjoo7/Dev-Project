# Miruni Flutter 마이그레이션 계획

> 작성일: 2026-03-19
> 현재 스택: Android Native (Kotlin, Jetpack Compose, Room, Hilt)
> 목표 스택: Flutter (Dart, Material 3, drift/sqflite, get_it/injectable)
> 목표: Android + iOS 멀티플랫폼 (iOS는 잠금화면 기능 제외)

---

## 1. 아키텍처 매핑

| 현재 (Android) | Flutter 대응 | 비고 |
|----------------|-------------|------|
| Clean Architecture + MVVM | Clean Architecture + BLoC/Riverpod | 구조 동일하게 유지 |
| Jetpack Compose | Flutter Widget | 선언형 UI 패턴 유사 |
| ViewModel + StateFlow | BLoC/Cubit 또는 Riverpod StateNotifier | 상태 관리 |
| Room Database | drift (권장) 또는 sqflite | drift가 타입 안전성 높음 |
| Hilt DI | get_it + injectable | 코드 생성 기반 DI |
| Navigation Compose | go_router | 선언적 라우팅 |
| SharedPreferences | shared_preferences | 동일 |
| Coroutines + Flow | Stream + async/await | Dart 네이티브 비동기 |
| strings.xml | ARB 파일 (flutter_localizations) | i18n 표준 |

---

## 2. 프로젝트 구조 (Flutter)

```
lib/
├── main.dart
├── app/
│   ├── app.dart                    # MaterialApp 설정
│   └── router.dart                 # go_router 설정
├── core/
│   ├── di/                         # get_it 모듈
│   ├── theme/                      # 테마, 색상, 타이포그래피
│   └── util/                       # DateTimeFormatUtil, DurationUnit 등
├── data/
│   ├── database/
│   │   ├── app_database.dart       # drift 데이터베이스
│   │   ├── task_dao.dart           # DAO
│   │   └── converters.dart         # 타입 변환
│   ├── entities/
│   │   ├── task_entity.dart
│   │   └── task_type.dart
│   └── repository/
│       ├── task_repository_impl.dart
│       └── shared_preference_repository_impl.dart
├── domain/
│   ├── model/
│   │   ├── todo_model.dart
│   │   ├── schedule_model.dart
│   │   └── task_model.dart
│   ├── repository/
│   │   ├── task_repository.dart
│   │   └── shared_preference_repository.dart
│   └── usecase/
│       ├── task/                    # 공통 UseCase
│       ├── todo/                    # Todo UseCase
│       ├── schedule/               # Schedule UseCase
│       └── setting/                # 설정 UseCase
├── presentation/
│   ├── home/
│   │   ├── home_screen.dart
│   │   ├── home_bloc.dart          # 또는 home_viewmodel.dart
│   │   └── widgets/
│   ├── calendar/
│   ├── overdue/
│   ├── add_task/
│   │   ├── add_todo/
│   │   └── add_schedule/
│   ├── detail/
│   │   ├── detail_todo/
│   │   └── detail_schedule/
│   ├── setup/
│   ├── unlock/                     # Android only
│   └── widget/                     # 공용 위젯 (BasicDialog, TimePicker 등)
├── service/
│   └── platform/
│       ├── unlock_service.dart     # Method Channel (Android only)
│       ├── foreground_service.dart  # Method Channel (Android only)
│       └── reminder_service.dart   # flutter_local_notifications
└── l10n/
    ├── app_ko.arb                  # 한국어
    └── app_en.arb                  # 영어 (선택)
```

---

## 3. 마이그레이션 단계

### Phase 1: 프로젝트 셋업 (1~2일)

- [ ] Flutter 프로젝트 생성
- [ ] 의존성 추가 (drift, get_it, go_router, flutter_bloc/riverpod 등)
- [ ] 프로젝트 구조 생성
- [ ] 테마/색상 설정 (기존 colors.xml 기반)
- [ ] i18n 설정 (기존 strings.xml → ARB 변환)

### Phase 2: Data 레이어 (2~3일)

- [ ] TaskEntity, TaskType 정의
- [ ] drift 데이터베이스 + DAO 구현
- [ ] TaskRepository 인터페이스 + 구현
- [ ] SharedPreferenceRepository 구현
- [ ] DI 모듈 구성 (get_it)

### Phase 3: Domain 레이어 (1~2일)

- [ ] TodoModel, ScheduleModel, TaskModel 정의
- [ ] Mapper 함수 작성
- [ ] UseCase 구현 (인터페이스 없이 직접 클래스로 간소화 가능)

### Phase 4: 공용 위젯 (2~3일)

- [ ] BasicDialog 구현
- [ ] WheelTimePicker 구현 (또는 flutter_time_picker 플러그인)
- [ ] DateRangePicker 구현 (Material DateRangePicker 활용)
- [ ] AlarmDisplayDatePicker 구현
- [ ] DateTimeFormatUtil, TaskLoadingHelper 포팅

### Phase 5: 화면 구현 (5~7일)

- [ ] SetupScreen (기상시간 입력)
- [ ] HomeScreen + ScheduleItem + ThingsToDoItem
- [ ] OverdueScreen
- [ ] CalendarScreen + TaskWidget
- [ ] AddTodoScreen + DatePicker
- [ ] AddScheduleScreen + DateRangePicker
- [ ] DetailTodoScreen
- [ ] DetailScheduleScreen
- [ ] MainScreen (BottomNavigation + Drawer)

### Phase 6: 서비스/플랫폼 (3~4일)

- [ ] 알림 서비스 (flutter_local_notifications + awesome_notifications)
- [ ] Android Method Channel: ForegroundService
- [ ] Android Method Channel: UnlockReceiver + UnlockActivity
- [ ] 플랫폼 분기 처리 (`Platform.isAndroid` / `Platform.isIOS`)

### Phase 7: 테스트 + 마무리 (2~3일)

- [ ] UseCase 단위 테스트
- [ ] BLoC/ViewModel 테스트
- [ ] Widget 테스트 (주요 화면)
- [ ] iOS 빌드 확인 + 플랫폼별 동작 검증
- [ ] 기존 Room DB → drift 데이터 마이그레이션 (기존 사용자 대응 시)

---

## 4. 주요 Flutter 의존성

```yaml
dependencies:
  flutter:
    sdk: flutter
  flutter_localizations:
    sdk: flutter

  # 상태 관리 (택 1)
  flutter_bloc: ^8.1.0          # BLoC 패턴
  # riverpod: ^2.5.0            # 또는 Riverpod

  # 라우팅
  go_router: ^14.0.0

  # DI
  get_it: ^7.7.0
  injectable: ^2.4.0

  # 데이터베이스
  drift: ^2.18.0
  sqlite3_flutter_libs: ^0.5.0

  # 로컬 저장소
  shared_preferences: ^2.2.0

  # 알림
  flutter_local_notifications: ^17.0.0
  awesome_notifications: ^0.9.0

  # UI
  intl: ^0.19.0                 # 날짜/시간 포맷
  table_calendar: ^3.1.0        # 캘린더 위젯 (선택)

dev_dependencies:
  build_runner: ^2.4.0
  drift_dev: ^2.18.0
  injectable_generator: ^2.6.0
  bloc_test: ^9.1.0             # BLoC 테스트
  mocktail: ^1.0.0              # 모킹
```

---

## 5. 플랫폼별 기능 분기

```dart
// lib/service/platform/platform_service.dart
import 'dart:io';

class PlatformService {
  /// 잠금화면 기능 사용 가능 여부
  bool get isUnlockScreenSupported => Platform.isAndroid;

  /// 포그라운드 서비스 사용 가능 여부
  bool get isForegroundServiceSupported => Platform.isAndroid;
}
```

### Android Method Channel (잠금화면 기능)

```dart
// lib/service/platform/unlock_service.dart
class UnlockService {
  static const _channel = MethodChannel('com.joo.miruni/unlock');

  Future<void> startForegroundService() async {
    if (!Platform.isAndroid) return;
    await _channel.invokeMethod('startForegroundService');
  }

  Future<void> stopForegroundService() async {
    if (!Platform.isAndroid) return;
    await _channel.invokeMethod('stopForegroundService');
  }
}
```

Android 네이티브 측(Kotlin)에서 기존 `ForegroundService`, `UnlockReceiver` 코드를 Method Channel Handler로 래핑하여 재사용.

---

## 6. 데이터 마이그레이션 (기존 사용자 대응)

기존 Android Room DB 사용자가 Flutter 버전으로 업데이트할 경우:

1. 첫 실행 시 Room DB 파일(`tasks` 테이블) 존재 확인
2. SQLite 직접 읽기로 데이터 추출
3. drift DB에 삽입
4. 구 DB 파일 삭제

```dart
Future<void> migrateFromRoomIfNeeded() async {
  final oldDbPath = join(await getDatabasesPath(), 'app_database');
  if (await File(oldDbPath).exists()) {
    // Room DB 직접 열어서 데이터 읽기 → drift에 삽입
  }
}
```

---

## 7. 예상 일정

| Phase | 작업 | 예상 기간 |
|-------|------|----------|
| 1 | 프로젝트 셋업 | 1~2일 |
| 2 | Data 레이어 | 2~3일 |
| 3 | Domain 레이어 | 1~2일 |
| 4 | 공용 위젯 | 2~3일 |
| 5 | 화면 구현 | 5~7일 |
| 6 | 서비스/플랫폼 | 3~4일 |
| 7 | 테스트 + 마무리 | 2~3일 |
| **합계** | | **16~24일** |

---

## 8. 리스크 및 주의사항

- **WheelTimePicker**: 커스텀 위젯이므로 Flutter로 직접 구현하거나 적절한 패키지 탐색 필요
- **정확한 알람**: Android에서는 `android_alarm_manager_plus`, iOS에서는 제한적 (Background Fetch)
- **포그라운드 서비스**: `flutter_foreground_task` 플러그인 안정성 확인 필요
- **삼성 디바이스 분기**: 알림 아이콘 등은 Method Channel로 처리
- **iOS 알림**: `awesome_notifications`가 iOS 지원하나, 정확한 알람 스케줄링은 OS 제약 존재
