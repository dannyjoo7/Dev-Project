package com.joo.miruni.presentation.addTask.addTodo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joo.miruni.domain.usecase.task.todo.AddTodoItemUseCase
import com.joo.miruni.presentation.util.DateTimeFormatUtil
import com.joo.miruni.presentation.util.DurationUnit
import com.joo.miruni.presentation.widget.Time
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddTodoViewModel @Inject constructor(
    private val addTodoItemUseCase: AddTodoItemUseCase,
) : ViewModel() {
    companion object {
        const val TAG = "AddTodoViewModel"

        const val MAX_TODO_LENGTH = 20
        const val MAX_DESCRIPTION_LENGTH = 100
    }

    /*
    * 변수
    * */

    // 할 일 텍스트
    private val _todoText = MutableStateFlow("")
    val todoText: StateFlow<String> = _todoText.asStateFlow()

    // 세부사항 텍스트
    private val _descriptionText = MutableStateFlow("")
    val descriptionText: StateFlow<String> = _descriptionText.asStateFlow()


    // 선택된 날짜
    private val _selectedDate = MutableStateFlow<LocalDate?>(LocalDate.now().plusDays(1))
    val selectedDate: StateFlow<LocalDate?> = _selectedDate.asStateFlow()

    // 선택된 시간
    private val _selectedTime = MutableStateFlow(getCurrentTimeIn5MinIntervals())
    val selectedTime: StateFlow<LocalTime> = _selectedTime.asStateFlow()

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


    // AddTodo 성공 여부
    private val _isTodoAdded = MutableStateFlow(false)
    val isTodoAdded: StateFlow<Boolean> = _isTodoAdded.asStateFlow()

    /*
    * 메소드
    * */

    /*
    * UI
    * */

    // 할 일 텍스트 업데이트
    fun updateTodoText(newValue: String) {
        _todoText.value = newValue.take(MAX_TODO_LENGTH)
    }

    // 세부사항 텍스트 업데이트
    fun updateDescriptionText(newValue: String) {
        _descriptionText.value = newValue.take(MAX_DESCRIPTION_LENGTH)
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

    // AlarmDisplayDatePicker 가시성 on/off
    fun clickedAlarmDisplayStartDateText() {
        _showAlarmDisplayStartDatePicker.value = !_showAlarmDisplayStartDatePicker.value
        _showDatePicker.value = false
        _showTimePicker.value = false
    }

    // 날짜 선택 메소드
    fun selectDate(date: LocalDate) {
        _selectedDate.value = date
        _showDatePicker.value = false
    }

    // 선택된 날짜 업데이트 메소드
    fun updateSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }

    // 선택된 알람 표시일 업데이트 메서드
    fun updateSelectedAlarmDisplayDate(amount: Int? = null, durationUnit: String? = null) {
        val currentValue = _selectedAlarmDisplayDate.value

        _selectedAlarmDisplayDate.value = AlarmDisplayDuration(
            amount = amount ?: currentValue.amount,
            unit = durationUnit ?: currentValue.unit
        )
    }

    // 애니메이션 종료
    fun finishAnimation() {
        _isTodoTextEmpty.value = false
    }

    /*
    * TimePicker
    * */

    // 시간 포멧
    fun convertLocalTimeToTime(localTime: LocalTime): Time {
        return DateTimeFormatUtil.convertLocalTimeToTime(localTime)
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
    }

    // 현재 시간을 5분 단위로 올림 조정
    private fun getCurrentTimeIn5MinIntervals(): LocalTime {
        return DateTimeFormatUtil.getCurrentTimeIn5MinIntervals()
    }

    /*
    * DatePicker
    * */

    // 월 변경 처리
    fun changeMonth(month: Int) {
        _selectedDate.value?.let {
            val currentYear = it.year

            val newDate = when {
                month < 1 -> {
                    LocalDate.of(currentYear.minus(1), 12, 1)
                }

                month > 12 -> {
                    LocalDate.of(currentYear.plus(1), 1, 1)
                }

                else -> {
                    LocalDate.of(currentYear, month, 1)
                }
            }

            _selectedDate.value = newDate
        }
    }


    // 연도 변경 처리
    fun changeYear(year: Int) {
        _selectedDate.value = _selectedDate.value?.withYear(year)
    }


    /*
    * Top Bar
    * */

    // 추가 버튼 클릭 시
    fun addTodoItem() {
        if (validateTodoItem()) {
            viewModelScope.launch {
                val todoItem = TodoItem(
                    id = 0,
                    title = _todoText.value,
                    descriptionText = _descriptionText.value,
                    selectedDate = combineDateAndTime(
                        _selectedDate.value ?: LocalDate.now().plusDays(1),
                        _selectedTime.value
                    ),
                    adjustedDate = calculateAdjustedDate(
                        _selectedDate.value ?: LocalDate.now(),
                        _selectedAlarmDisplayDate.value
                    ),
                    isComplete = false,
                    isPinned = false,
                )

                runCatching {
                    addTodoItemUseCase(todoItem)
                }.onSuccess {
                    _isTodoAdded.value = true
                }.onFailure { exception ->
                    _isTodoAdded.value = false
                    Log.e(TAG, exception.message.toString())
                }
            }
        } else {
            return
        }
    }

    // 무결성 검사
    private fun validateTodoItem(): Boolean {
        // 제목이 비어있는지 체크
        if (_todoText.value.isEmpty()) {
            _isTodoTextEmpty.value = true
            return false
        }
        return true
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

    // 시간과 날짜 합치는 메소드
    private fun combineDateAndTime(date: LocalDate, time: LocalTime): LocalDateTime {
        return date.atTime(time)
    }


    /*
    * 초기화
    * */
    fun setSelectedDate(date: LocalDate) {
        _selectedDate.value = date
    }
}


