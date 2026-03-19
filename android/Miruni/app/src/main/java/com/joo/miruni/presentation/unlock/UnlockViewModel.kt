package com.joo.miruni.presentation.unlock

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joo.miruni.domain.usecase.task.schedule.GetScheduleItemsUseCase
import com.joo.miruni.domain.usecase.task.todo.GetTodoItemsForAlarmUseCase
import com.joo.miruni.domain.usecase.setting.SettingObserveCompletedItemsVisibilityUseCase
import com.joo.miruni.presentation.home.Schedule
import com.joo.miruni.presentation.home.ThingsTodo
import com.joo.miruni.presentation.util.DateTimeFormatUtil
import com.joo.miruni.presentation.util.TaskLoadingHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class UnlockViewModel @Inject constructor(
    private val getTodoItemsForAlarmUseCase: GetTodoItemsForAlarmUseCase,
    private val getScheduleItemsForAlarmUseCase: GetScheduleItemsUseCase,
    private val settingObserveCompletedItemsVisibilityUseCase: SettingObserveCompletedItemsVisibilityUseCase,
) : ViewModel() {
    companion object {
        const val TAG = "UnlockViewModel"
    }

    /*
    * Live Date
    * */

    // 현재 시간
    private val _curDateTime = MutableStateFlow<LocalDateTime>(LocalDateTime.now())
    val curDateTime: StateFlow<LocalDateTime> get() = _curDateTime.asStateFlow()

    // 할 일 Item list
    private val _thingsTodoItems = MutableStateFlow<List<ThingsTodo>>(emptyList())
    val thingsTodoItems: StateFlow<List<ThingsTodo>> get() = _thingsTodoItems.asStateFlow()

    // 일정 Item list
    private val _scheduleItems = MutableStateFlow<List<Schedule>>(emptyList())
    val scheduleItems: StateFlow<List<Schedule>> get() = _scheduleItems.asStateFlow()

    // todoList 로딩 중인지 판단하는 변수
    private val _isTodoListLoading = MutableStateFlow(false)
    val isTodoListLoading: StateFlow<Boolean> get() = _isTodoListLoading.asStateFlow()

    // scheduleList 로딩 중인지 판단하는 변수
    private val _isScheduleListLoading = MutableStateFlow(false)
    val isScheduleListLoading: StateFlow<Boolean> get() = _isScheduleListLoading.asStateFlow()

    // 완료 항목 값
    private val _settingObserveCompleteVisibility = MutableStateFlow<Boolean>(false)
    val settingObserveCompleteVisibility: StateFlow<Boolean> get() = _settingObserveCompleteVisibility.asStateFlow()

    // scheduleList 페이징을 위한 마지막 데이터의 startDate
    private var lastStartDate: LocalDate? = null

    // 시간 업데이트 Job
    private var timeUpdateJob: Job? = null

    init {
        loadTodoItemsForAlarm()
        loadInitialScheduleData()
        loadUserSetting()
        startUpdatingTime()
    }


    /*
    * 할 일
    * */

    // 할 일 load 메소드
    private fun loadTodoItemsForAlarm() {
        viewModelScope.launch {
            _isTodoListLoading.value = true
            runCatching {
                getTodoItemsForAlarmUseCase.invoke(
                    _curDateTime.value
                )
            }.onSuccess { flow ->
                flow.collect { todoItems ->
                    _thingsTodoItems.value =
                        TaskLoadingHelper.sortTodoItems(
                            todoItems.todoEntities.map { TaskLoadingHelper.mapToThingsTodo(it) }
                        )

                    _isTodoListLoading.value = false
                }
            }.onFailure { exception ->
                exception.printStackTrace()
                _isTodoListLoading.value = false
            }
        }
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
                    _curDateTime.value.toLocalDate(),
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
                    _curDateTime.value.toLocalDate(),
                    lastStartDate
                )
            }.onSuccess { flow ->
                flow.collect { scheduleItems ->
                    _scheduleItems.value =
                        (_scheduleItems.value +
                            scheduleItems.scheduleEntities.map { TaskLoadingHelper.mapToSchedule(it) }
                                .filterNot { newSchedule ->
                                    _scheduleItems.value.any { existingSchedule ->
                                        existingSchedule.id == newSchedule.id
                                    }
                                }
                        )
                            .sortedWith(compareByDescending<Schedule> { it.isPinned }.thenBy { it.startDate })
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

    /*
    * UI
    * */

    // 날짜 Text 포멧
    fun formatSelectedDate(date: LocalDate): String {
        return DateTimeFormatUtil.formatDateWithRelative(date)
    }

    // 시간 업데이트
    private fun startUpdatingTime() {
        timeUpdateJob?.cancel()
        timeUpdateJob = viewModelScope.launch {
            while (true) {
                _curDateTime.value = LocalDateTime.now()
                delay(1000)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timeUpdateJob?.cancel()
    }

}




