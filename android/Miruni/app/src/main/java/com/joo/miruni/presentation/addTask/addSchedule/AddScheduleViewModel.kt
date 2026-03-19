package com.joo.miruni.presentation.addTask.addSchedule

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.joo.miruni.domain.usecase.task.schedule.AddScheduleItemUseCase
import com.joo.miruni.presentation.util.DurationUnit
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AddScheduleViewModel @Inject constructor(
    private val addScheduleItemUseCase: AddScheduleItemUseCase,
) : ViewModel() {
    companion object {
        const val TAG = "AddScheduleViewModel"

        const val MAX_TODO_LENGTH = 20
        const val MAX_DESCRIPTION_LENGTH = 100
    }

    init {

    }

    /*
    * 변수
    * */

    // 일정 텍스트
    private val _titleText = MutableStateFlow("")
    val titleText: StateFlow<String> = _titleText.asStateFlow()

    // 세부사항 텍스트
    private val _descriptionText = MutableStateFlow("")
    val descriptionText: StateFlow<String> = _descriptionText.asStateFlow()


    // 선택된 시작 날짜
    private val _selectedStartDate = MutableStateFlow<LocalDate?>(null)
    val selectedStartDate: StateFlow<LocalDate?> = _selectedStartDate.asStateFlow()

    // 선택된 종료 날짜
    private val _selectedEndDate = MutableStateFlow<LocalDate?>(null)
    val selectedEndDate: StateFlow<LocalDate?> = _selectedEndDate.asStateFlow()


    // Bool 시작 날짜 선택 진행 유뮤
    private val _showDateRangePicker = MutableStateFlow(false)
    val showDateRangePicker: StateFlow<Boolean> = _showDateRangePicker.asStateFlow()


    // TodoTextEmpty 무결성 검사
    private val _isTitleTextEmpty = MutableStateFlow(false)
    val isTitleTextEmpty: StateFlow<Boolean> = _isTitleTextEmpty.asStateFlow()

    // 날짜 무결성 검사
    private val _isDateEmpty = MutableStateFlow(false)
    val isDateEmpty: StateFlow<Boolean> = _isDateEmpty.asStateFlow()


    // AddSchedule 성공 여부
    private val _isScheduleAdded = MutableStateFlow(false)
    val isScheduleAdded: StateFlow<Boolean> = _isScheduleAdded.asStateFlow()

    /*
    * UI
    * */

    // 할 일 텍스트 업데이트
    fun updateTitleText(newValue: String) {
        _titleText.value = newValue.take(MAX_TODO_LENGTH)
    }

    // 세부사항 텍스트 업데이트
    fun updateDescriptionText(newValue: String) {
        _descriptionText.value = newValue.take(MAX_DESCRIPTION_LENGTH)
    }

    // StartDatePicker 가시성 on/off
    fun clickedDateRangePickerBtn() {
        _showDateRangePicker.value = !_showDateRangePicker.value
    }

    // AlarmDisplayDatePicker 가시성 on/off
    fun clickedAlarmDisplayStartDateText() {
        _showDateRangePicker.value = false
    }

    /*
    * DatePicker
    * */

    // 날짜 선택 초기화
    fun initSelectedDate() {
        _selectedStartDate.value = null
        _selectedEndDate.value = null
    }

    // 시작 날짜 선택 메소드
    fun selectStartDate(date: LocalDate) {
        // 종료일이 선택되지 않은 경우
        if (_selectedEndDate.value == null) {
            _selectedStartDate.value = date
        }
        // 종료일이 선택된 경우
        else {
            // 종료일이 시작일보다 이전인 경우 종료일을 초기화
            if (_selectedEndDate.value?.isBefore(date) == true) {
                _selectedEndDate.value = null
            } else {
                // 정상적으로 시작일 설정
                _selectedStartDate.value = date
            }
        }
    }

    // 종료 날짜 선택 메소드
    fun selectEndDate(date: LocalDate) {
        // 시작일이 선택되지 않은 경우
        if (_selectedStartDate.value == null) {
            return
        }
        // 종료일이 시작일보다 이전인 경우
        else if (_selectedStartDate.value!!.isAfter(date)) {
            // 종료일을 선택할 수 없으므로 아무 것도 하지 않음
            return
        } else {
            // 정상적으로 종료일 설정
            _selectedEndDate.value = date
        }
    }

    // date MM월 dd일 yyyy 변환 메소드
    fun formatSelectedDateForCalendar(selectDate: LocalDate?): String? {
        return selectDate?.format(
            java.time.format.DateTimeFormatter.ofPattern("M월 d일 yyyy", java.util.Locale.KOREA)
        )
    }

    /*
    * Top Bar
    * */

    // 추가 버튼 클릭 시
    fun addScheduleItem() {
        if (validateScheduleItem()) {
            viewModelScope.launch {
                val scheduleItem = ScheduleItem(
                    id = 0,
                    title = _titleText.value,
                    descriptionText = _descriptionText.value,
                    startDate = _selectedStartDate.value ?: LocalDate.now().plusDays(1),
                    endDate = _selectedEndDate.value ?: LocalDate.now().plusDays(1),
                    isComplete = false,
                    isPinned = false
                )
                runCatching {
                    addScheduleItemUseCase(scheduleItem)
                }.onSuccess {
                    _isScheduleAdded.value = true
                }.onFailure { exception ->
                    _isScheduleAdded.value = false
                    Log.e(TAG, exception.message.toString())
                }
            }
        } else {
            return
        }
    }

    // 무결성 검사
    private fun validateScheduleItem(): Boolean {
        // 제목이 비어있는지 체크
        if (_titleText.value.isEmpty()) {
            _isTitleTextEmpty.value = true
            return false
        }

        // 시작일과 종료일이 유효한지 체크
        if (_selectedStartDate.value == null || _selectedEndDate.value == null) {
            _isDateEmpty.value = true
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

    // 애니메이션 종료
    fun finishAnimation() {
        _isTitleTextEmpty.value = false
        _isDateEmpty.value = false
    }

    /*
    * 초기화
    * */
    fun setSelectedDate(date: LocalDate) {
        _selectedStartDate.value = date
    }
}


