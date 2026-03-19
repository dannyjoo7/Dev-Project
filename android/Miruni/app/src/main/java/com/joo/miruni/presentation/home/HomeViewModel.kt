package com.joo.miruni.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joo.miruni.domain.usecase.task.CancelCompleteTaskItemUseCase
import com.joo.miruni.domain.usecase.task.CompleteTaskItemUseCase
import com.joo.miruni.domain.usecase.task.todo.DelayTodoItemUseCase
import com.joo.miruni.domain.usecase.task.DeleteTaskItemUseCase
import com.joo.miruni.domain.usecase.task.schedule.GetScheduleItemsUseCase
import com.joo.miruni.domain.usecase.task.todo.GetTodoItemsForAlarmUseCase
import com.joo.miruni.domain.usecase.setting.SettingObserveCompletedItemsVisibilityUseCase
import com.joo.miruni.domain.usecase.task.TogglePinStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import com.joo.miruni.presentation.util.DateTimeFormatUtil
import com.joo.miruni.presentation.util.TaskLoadingHelper
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTodoItemsForAlarmUseCase: GetTodoItemsForAlarmUseCase,
    private val getScheduleItemsForAlarmUseCase: GetScheduleItemsUseCase,
    private val deleteTaskItemUseCase: DeleteTaskItemUseCase,
    private val completeTaskItemUseCase: CompleteTaskItemUseCase,
    private val cancelCompleteTaskItemUseCase: CancelCompleteTaskItemUseCase,
    private val delayTodoItemUseCase: DelayTodoItemUseCase,
    private val settingObserveCompletedItemsVisibilityUseCase: SettingObserveCompletedItemsVisibilityUseCase,
    private val togglePinStatusUseCase: TogglePinStatusUseCase,
) : ViewModel() {
    companion object {
        const val TAG = "HomeViewModel"
    }

    /*
    * State Flow
    * */

    // 선택된 날짜
    private val _selectDate = MutableStateFlow(LocalDateTime.now())
    val selectDate: StateFlow<LocalDateTime> = _selectDate.asStateFlow()

    // 할 일 Item list
    private val _thingsTodoItems = MutableStateFlow<List<ThingsTodo>>(emptyList())
    val thingsTodoItems: StateFlow<List<ThingsTodo>> = _thingsTodoItems.asStateFlow()

    // 일정 Item list
    private val _scheduleItems = MutableStateFlow<List<Schedule>>(emptyList())
    val scheduleItems: StateFlow<List<Schedule>> = _scheduleItems.asStateFlow()

    // todoList 로딩 중인지 판단하는 변수
    private val _isTodoListLoading = MutableStateFlow(false)
    val isTodoListLoading: StateFlow<Boolean> = _isTodoListLoading.asStateFlow()

    // scheduleList 로딩 중인지 판단하는 변수
    private val _isScheduleListLoading = MutableStateFlow(false)
    val isScheduleListLoading: StateFlow<Boolean> = _isScheduleListLoading.asStateFlow()

    // 미래일 판단 변수
    private val _isFutureDate = MutableStateFlow(false)
    val isFutureDate: StateFlow<Boolean> = _isFutureDate.asStateFlow()

    // 확장 여부를 판단하는 변수
    private val _expandedItems = mutableStateOf<Set<Long>>(emptySet())
    val expandedItems: State<Set<Long>> = _expandedItems

    // 삭제됐는지 여부를 판단하는 변수
    private val _deletedItems = MutableStateFlow<Set<Long>>(emptySet())
    val deletedItems: StateFlow<Set<Long>> = _deletedItems.asStateFlow()

    // 완료 항목 값
    private val _settingObserveCompleteVisibility = MutableStateFlow(false)
    val settingObserveCompleteVisibility: StateFlow<Boolean> = _settingObserveCompleteVisibility.asStateFlow()

    // 스크롤 끝인지 판단하는 변수
    private val _isEndOfScroll = MutableStateFlow(false)
    val isEndOfScroll: StateFlow<Boolean> = _isEndOfScroll.asStateFlow()

    // todoList 페이징을 위한 마지막 데이터의 deadLine
    private var lastDataDeadLine: LocalDateTime? = null

    // scheduleList 페이징을 위한 마지막 데이터의 startDate
    private var lastStartDate: LocalDate? = null

    // getThings 코루틴
    private var getTodoItemsJob: Job? = null

    init {
        loadTodoItemsForAlarm()
        loadInitialScheduleData()
        loadUserSetting()
    }


    /*
    * 할 일
    * */

    // 할 일 load 메소드
    private fun loadTodoItemsForAlarm() {
        getTodoItemsJob?.cancel()
        getTodoItemsJob = viewModelScope.launch {
            _isTodoListLoading.value = true
            runCatching {
                getTodoItemsForAlarmUseCase.invoke(
                    _selectDate.value
                )
            }.onSuccess { flow ->
                flow.collect { todoItems ->
                    _thingsTodoItems.value =
                        TaskLoadingHelper.sortTodoItems(
                            todoItems.todoEntities.map { TaskLoadingHelper.mapToThingsTodo(it) }
                        )


                    lastDataDeadLine = _thingsTodoItems.value.lastOrNull()?.deadline
                    _isTodoListLoading.value = false
                }
            }.onFailure { exception ->
                exception.printStackTrace()
                _isTodoListLoading.value = false
            }
        }
    }

    // Task 삭제 메소드
    fun deleteTaskItem(id: Long) {
        viewModelScope.launch {
            _deletedItems.value = _deletedItems.value + id
            runCatching {
                // 애니매이션을 위한 딜레이
                delay(1000)
                deleteTaskItemUseCase.invoke(id)
            }.onSuccess {
                _expandedItems.value = _expandedItems.value.filterNot { it == id }.toSet()
            }.onFailure {
                _deletedItems.value = _deletedItems.value - id
            }
        }
    }

    // Task 완료 시
    fun completeTask(taskId: Long) {
        viewModelScope.launch {
            runCatching {
                completeTaskItemUseCase.invoke(taskId, LocalDateTime.now())
            }.onSuccess {
                Log.d(TAG, "Complete selectDate = ${selectDate.value}")
            }.onFailure {
                Log.e(TAG, "Failed to complete task", it)
            }
        }
    }

    // Task 완료 취소 시
    fun completeCancelTaskItem(taskId: Long) {
        viewModelScope.launch {
            runCatching {
                cancelCompleteTaskItemUseCase.invoke(taskId)
            }.onSuccess {
                Log.d(TAG, "Cancel Complete selectDate = ${selectDate.value}")
            }.onFailure {
                Log.e(TAG, "Failed to cancel complete task", it)
            }
        }
    }

    // 미루기 메소드
    fun delayTodoItem(thingsTodo: ThingsTodo) {
        viewModelScope.launch {
            runCatching {
                // TODO deadLine 미룰 때 기기에 저장된 유저가 설정한 값으로 대체 -> 현재 1에서 userSetting 값으로...
                delayTodoItemUseCase.invoke(thingsTodo.id, thingsTodo.deadline.plusDays(1))
            }.onFailure {
                Log.e(TAG, "Failed to delay todo item", it)
            }
        }
    }

    // 고정 메소드
    fun togglePinStatus(taskId: Long) {
        viewModelScope.launch {
            runCatching {
                togglePinStatusUseCase.invoke(taskId)
            }.onFailure {
                Log.e(TAG, "Failed to toggle pin status", it)
            }
        }
    }

    /*
    * UI
    * */

    // 화면 초기화 메소드
    fun refreshScreen() {
        loadTodoItemsForAlarm()
        loadInitialScheduleData()
        loadUserSetting()
        _selectDate.value = LocalDateTime.now()
    }

    // 날짜 바꾸는 메소드
    fun changeDate(op: DateChange) {
        _thingsTodoItems.value = emptyList()
        _selectDate.value = when (op) {
            DateChange.RIGHT -> LocalDateTime.of(
                // 자정으로 설정
                _selectDate.value.toLocalDate(),
                LocalTime.MIDNIGHT
            ).plusDays(1)

            DateChange.LEFT -> LocalDateTime.of(
                // 자정으로 설정
                _selectDate.value.toLocalDate(),
                LocalTime.MIDNIGHT
            ).minusDays(1)
        }

        // 바뀐 날짜가 오늘이면 자정이 아닌 현재 시간 적용
        if (_selectDate.value.toLocalDate() == LocalDate.now()) {
            _selectDate.value = LocalDateTime.now()
        }

        collapseAllItems()

        loadTodoItemsForAlarm()
        checkFutureDate()
        lastDataDeadLine = null
    }

    // TodoTings 확장 토글 메소드
    fun toggleItemExpansion(id: Long) {
        _expandedItems.value = if (_expandedItems.value.contains(id)) {
            emptySet()
        } else {
            setOf(id)
        }
    }

    // TodoTings가 모든 확장 해제 메소드
    fun collapseAllItems() {
        _expandedItems.value = emptySet()
    }

    // 날짜 Text 포멧
    fun formatSelectedDate(date: LocalDateTime): String {
        return DateTimeFormatUtil.formatDateTimeWithRelative(date)
    }

    // 미래일 판단 메소드
    private fun checkFutureDate() {
        _isFutureDate.value = _selectDate.value.isAfter(LocalDateTime.now())
    }

    /*
    * 일정
    * */

    // 일정 초기 로드 메소드
    private fun loadInitialScheduleData() {
        viewModelScope.launch {
            _isScheduleListLoading.value = true
            runCatching {
                getScheduleItemsForAlarmUseCase.invoke(
                    _selectDate.value.toLocalDate(),
                    null
                )
            }.onSuccess { flow ->
                flow.collect { scheduleItems ->
                    _scheduleItems.value = TaskLoadingHelper.sortScheduleItems(
                        scheduleItems.scheduleEntities.map { TaskLoadingHelper.mapToSchedule(it) }
                    )

                    lastStartDate = _scheduleItems.value.lastOrNull()?.startDate
                    _isScheduleListLoading.value = false
                }

            }.onFailure { exception ->
                exception.printStackTrace()
                _isScheduleListLoading.value = false
            }
        }
    }

    // 일정 more load 메소드
    fun loadMoreScheduleData() {
        viewModelScope.launch {
            _isScheduleListLoading.value = true
            runCatching {
                getScheduleItemsForAlarmUseCase.invoke(
                    _selectDate.value.toLocalDate(),
                    lastStartDate
                )
            }.onSuccess { flow ->
                flow.collect { scheduleItems ->
                    _scheduleItems.value =
                        (_scheduleItems.value + scheduleItems.scheduleEntities.map { TaskLoadingHelper.mapToSchedule(it) }
                                .filterNot { newSchedule ->
                                    _scheduleItems.value.any { existingSchedule ->
                                        existingSchedule.id == newSchedule.id
                                    }
                                })
                            .sortedWith(compareByDescending<Schedule> { it.isPinned }.thenBy { it.startDate })

                    Log.d(TAG, "추가 ${_scheduleItems.value}")

                    lastStartDate = _scheduleItems.value.lastOrNull()?.startDate
                    _isScheduleListLoading.value = false
                }
            }.onFailure { exception ->
                exception.printStackTrace()
                _isScheduleListLoading.value = false
            }
        }
    }


    /*
    * 유저 설정
    * */

    private fun loadUserSetting() {
        viewModelScope.launch {
            runCatching {
                settingObserveCompletedItemsVisibilityUseCase.invoke()
            }.onSuccess { flow ->
                flow.collect { visibility ->
                    _settingObserveCompleteVisibility.value = visibility
                }
            }.onFailure { exception ->
                Log.e(TAG, "Failed to load settings", exception)
            }
        }
    }


}




