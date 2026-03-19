package com.joo.miruni.presentation.detail.detailTodo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joo.miruni.domain.usecase.task.CancelCompleteTaskItemUseCase
import com.joo.miruni.domain.usecase.task.CompleteTaskItemUseCase
import com.joo.miruni.domain.usecase.task.DeleteTaskItemUseCase
import com.joo.miruni.domain.usecase.task.todo.GetTodoItemByIDUseCase
import com.joo.miruni.domain.usecase.task.todo.UpdateTodoItemUseCase
import com.joo.miruni.presentation.addTask.addTodo.AlarmDisplayDuration
import com.joo.miruni.presentation.addTask.addTodo.TodoItem
import com.joo.miruni.presentation.addTask.addTodo.toTodoItem
import com.joo.miruni.presentation.util.DateTimeFormatUtil
import com.joo.miruni.presentation.util.DurationUnit
import com.joo.miruni.presentation.widget.Time
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject
import kotlin.math.absoluteValue

@HiltViewModel
class DetailTodoViewModel @Inject constructor(
    private val getTodoItemByIDUseCase: GetTodoItemByIDUseCase,
    private val updateTodoItemUseCase: UpdateTodoItemUseCase,
    private val deleteTaskItemUseCase: DeleteTaskItemUseCase,
    private val completeTaskItemUseCase: CompleteTaskItemUseCase,
    private val cancelCompleteTaskItemUseCase: CancelCompleteTaskItemUseCase,
) : ViewModel() {
    companion object {
        const val TAG = "DetailTodoViewModel"

        const val MAX_TODO_LENGTH = 20
        const val MAX_DESCRIPTION_LENGTH = 100
    }

    /*
    * 변수
    * */

    // TodoItem
    private val _todoItem = MutableStateFlow<TodoItem?>(null)
    val todoItem: StateFlow<TodoItem?> = _todoItem.asStateFlow()

    // 수정 됐는지 판단 변수
    private val _isModified = MutableStateFlow(false)
    val isModified: StateFlow<Boolean> = _isModified.asStateFlow()

    // 할 일 텍스트
    private val _todoText = MutableStateFlow("")
    val todoText: StateFlow<String> = _todoText.asStateFlow()

    // 세부사항 텍스트
    private val _descriptionText = MutableStateFlow("")
    val descriptionText: StateFlow<String> = _descriptionText.asStateFlow()


    // 선택된 날짜
    private val _selectedDate = MutableStateFlow<LocalDate?>(null)
    val selectedDate: StateFlow<LocalDate?> = _selectedDate.asStateFlow()

    // 선택된 시간
    private val _selectedTime = MutableStateFlow<LocalTime?>(null)
    val selectedTime: StateFlow<LocalTime?> = _selectedTime.asStateFlow()

    // 선택된 알람 표시 시간
    private val _selectedAlarmDisplayDate = MutableStateFlow(
        AlarmDisplayDuration(1, DurationUnit.WEEK)
    )
    val selectedAlarmDisplayDate: StateFlow<AlarmDisplayDuration> = _selectedAlarmDisplayDate.asStateFlow()


    // Bool 날짜 선택 진행 유뮤
    private val _showDatePicker = MutableStateFlow(false)
    val showDatePicker: StateFlow<Boolean> = _showDatePicker.asStateFlow()

    // Bool 시간 선택 진행 유뮤
    private val _showTimePicker = MutableStateFlow(false)
    val showTimePicker: StateFlow<Boolean> = _showTimePicker.asStateFlow()

    // Bool 알람 표시 시작일 선택 진행 유뮤
    private val _showAlarmDisplayStartDatePicker = MutableStateFlow(false)
    val showAlarmDisplayStartDatePicker: StateFlow<Boolean> = _showAlarmDisplayStartDatePicker.asStateFlow()


    // TodoTextEmpty 애니매이션
    private val _isTodoTextEmpty = MutableStateFlow(false)
    val isTodoTextEmpty: StateFlow<Boolean> = _isTodoTextEmpty.asStateFlow()


    // updateTodo 성공 여부
    private val _isTodoUpdate = MutableStateFlow(false)
    val isTodoUpdate: StateFlow<Boolean> = _isTodoUpdate.asStateFlow()


    /*
    * 메소드
    * */

    // init 메소드
    fun loadTodoDetails(todoId: Long) {
        viewModelScope.launch {
            runCatching {
                getTodoItemByIDUseCase(todoId)
            }.onSuccess { todoModel ->
                // TodoItem으로 변환
                _todoItem.value = todoModel.toTodoItem()

                // 속성 매칭
                _todoText.value = todoModel.title
                _descriptionText.value = todoModel.details ?: ""
                _selectedDate.value =
                    todoModel.deadLine?.toLocalDate() ?: LocalDate.now().plusDays(1)
                _selectedTime.value = todoModel.deadLine?.toLocalTime() ?: LocalTime.now()

                // 알람 표시 날짜 계산 후 사용 예시
                val (amount, unit) = calculateDistanceOfDateUnit(
                    todoModel.deadLine?.toLocalDate() ?: LocalDate.now(),
                    todoModel.alarmDisplayDate
                )
                _selectedAlarmDisplayDate.value = AlarmDisplayDuration(amount, unit)


            }.onFailure { exception ->
                Log.e(TAG, exception.toString())
            }
        }
    }

    /*
    * UI
    * */

    // 할 일 텍스트 업데이트
    fun updateTodoText(newValue: String) {
        _todoText.value = newValue.take(MAX_TODO_LENGTH)
        isModify()
    }

    // 세부사항 텍스트 업데이트
    fun updateDescriptionText(newValue: String) {
        _descriptionText.value = newValue.take(MAX_DESCRIPTION_LENGTH)
        isModify()
    }

    // DatePicker 가시성 on/off
    fun clickedDatePickerBtn() {
        _showDatePicker.value = !_showDatePicker.value
        _showTimePicker.value = false
        _showAlarmDisplayStartDatePicker.value = false
    }

    // TimePicker 가시성 on/off
    fun clickedTimePickerBtn() {
        _showTimePicker.value = !_showTimePicker.value
        _showDatePicker.value = false
        _showAlarmDisplayStartDatePicker.value = false
    }

    //  AlarmDisplayDatePicker 가시성 on/off
    fun clickedAlarmDisplayStartDateText() {
        _showAlarmDisplayStartDatePicker.value = !_showAlarmDisplayStartDatePicker.value
        _showDatePicker.value = false
        _showTimePicker.value = false
    }

    // 날짜 선택 메소드
    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
        _showDatePicker.value = false
        isModify()
    }

    // 선택된 날짜 업데이트 메소드
    fun updateSelectedDate(date: LocalDate?) {
        _selectedDate.value = date
    }

    // 선택된 알람 표시일 업데이트 메서드
    fun updateSelectedAlarmDisplayDate(amount: Int? = null, durationUnit: String? = null) {
        val currentValue = _selectedAlarmDisplayDate.value

        _selectedAlarmDisplayDate.value = AlarmDisplayDuration(
            amount = amount ?: currentValue.amount,
            unit = durationUnit ?: currentValue.unit
        )
        isModify()
    }

    // 애니메이션 종료
    fun finishAnimation() {
        _isTodoTextEmpty.value = false
    }

    /*
    * TimePicker
    * */

    // 시간 포멧
    fun convertLocalTimeToTime(): Time? {
        val selectedTime = _selectedTime.value ?: return null
        return DateTimeFormatUtil.convertLocalTimeToTime(selectedTime)
    }

    // 날짜 Text 포멧
    fun formatSelectedDate(date: LocalDate): String {
        return DateTimeFormatUtil.formatDateWithRelative(date)
    }

    // 시간 Text 포멧
    fun formatLocalTimeToString(localTime: LocalTime): String {
        return DateTimeFormatUtil.formatLocalTimeToString(localTime)
    }

    // 선택된 시간 업데이트 메서드
    fun updateSelectedTime(hour: Int, minute: Int, format: String) {
        _selectedTime.value = DateTimeFormatUtil.toLocalTime(hour, minute, format)
        isModify()
    }

    /*
    * DatePicker
    * */

    // MM월 yyyy 변환 메소드
    fun formatSelectedDateForCalendar(): String {
        return _selectedDate.value?.format(
            java.time.format.DateTimeFormatter.ofPattern("M월 yyyy", java.util.Locale.KOREA)
        ) ?: ""
    }

    // 월 변경 처리
    fun changeMonth(month: Int) {
        _selectedDate.value?.let {
            val newDate = if (month > 12) {
                LocalDate.of(it.year + 1, 1, 1)
            } else {
                LocalDate.of(it.year, month, 1)
            }
            _selectedDate.value = newDate
        }
    }

    /*
    * Top Bar
    * */

    // 수정 버튼 클릭 시
    fun updateTodoItem() {
        viewModelScope.launch {
            // TodoItem ID가 null인 경우
            if (_todoItem.value?.id == null) {
                Log.e(TAG, "Todo item ID is null.")
                return@launch
            }

            // Todo 텍스트가 비어있는 경우
            if (_todoText.value.isEmpty()) {
                _isTodoTextEmpty.value = true
                delay(600)
                _isTodoTextEmpty.value = false
                return@launch
            }

            // TodoItem 생성
            val todoItem = TodoItem(
                id = _todoItem.value!!.id,
                title = _todoText.value,
                descriptionText = _descriptionText.value,
                selectedDate = combineDateAndTime(
                    _selectedDate.value ?: LocalDate.now().plusDays(1),
                    _selectedTime.value ?: LocalTime.now()
                ),
                adjustedDate = calculateAdjustedDate(
                    _selectedDate.value ?: LocalDate.now(),
                    _selectedAlarmDisplayDate.value
                ),
                isComplete = _todoItem.value?.isComplete ?: false,
                isPinned = _todoItem.value?.isPinned ?: false
            )

            runCatching {
                updateTodoItemUseCase(todoItem)
            }.onSuccess {
                _isTodoUpdate.value = true
            }.onFailure { exception ->
                _isTodoUpdate.value = false
                Log.e(TAG, exception.message.toString())
            }
        }
    }

    // 알람 표시 시작일 계산 메소드
    private fun calculateAdjustedDate(
        currentDate: LocalDate,
        duration: AlarmDisplayDuration,
    ): LocalDate {
        if (duration.amount == null) {
            return currentDate.minusWeeks(1)
        }
        return when (duration.unit) {
            DurationUnit.DAY -> currentDate.minusDays(duration.amount.toLong())
            DurationUnit.WEEK -> currentDate.minusWeeks(duration.amount.toLong())
            DurationUnit.MONTH -> currentDate.minusMonths(duration.amount.toLong())
            DurationUnit.YEAR -> currentDate.minusYears(duration.amount.toLong())
            else -> currentDate
        }
    }

    // 두 날짜가 얼마나 차이나는지 유닛을 계산 및 반환해주는 메소드
    private fun calculateDistanceOfDateUnit(
        deadLine: LocalDate,
        alarmDate: LocalDate,
    ): Pair<Int, String> {
        val daysDifference = deadLine.toEpochDay() - alarmDate.toEpochDay()

        return when {
            daysDifference == 0L -> Pair(0, DurationUnit.DAY) // 같은 날
            daysDifference < 0 -> Pair(daysDifference.toInt().absoluteValue, DurationUnit.DAY) // 과거
            daysDifference < 7 -> Pair(daysDifference.toInt(), DurationUnit.DAY) // 7일 이내
            daysDifference < 30 -> Pair((daysDifference / 7).toInt(), DurationUnit.WEEK) // 30일 이내
            daysDifference < 365 -> Pair((daysDifference / 30).toInt(), DurationUnit.MONTH) // 1년 이내
            else -> Pair((daysDifference / 365).toInt(), DurationUnit.YEAR) // 1년 이상
        }
    }


    // 시간과 날짜 합치는 메소드
    private fun combineDateAndTime(date: LocalDate, time: LocalTime): LocalDateTime {
        return date.atTime(time)
    }

    // 항목이 수정됨
    private fun isModify() {
        _isModified.value = true
    }

    /*
   * Bottom Bar
   * */

    // 일정 삭제
    fun deleteTodoItem(todoId: Long) {
        viewModelScope.launch {
            runCatching {
                deleteTaskItemUseCase.invoke(todoId)
            }.onFailure {
                Log.e(TAG, "Failed to delete todo item", it)
            }
        }
    }

    // 일정 완료
    fun completeTodoItem(todoId: Long) {
        viewModelScope.launch {
            runCatching {
                completeTaskItemUseCase.invoke(todoId, LocalDateTime.now())
            }.onSuccess {
                loadTodoDetails(todoId)
            }.onFailure {
                Log.e(TAG, "Failed to complete todo item", it)
            }
        }
    }

    // 일정 완료 취소
    fun completeCancelTodoItem(todoId: Long) {
        viewModelScope.launch {
            runCatching {
                cancelCompleteTaskItemUseCase.invoke(todoId)
            }.onSuccess {
                loadTodoDetails(todoId)
            }.onFailure {
                Log.e(TAG, "Failed to cancel complete todo item", it)
            }
        }
    }
}


