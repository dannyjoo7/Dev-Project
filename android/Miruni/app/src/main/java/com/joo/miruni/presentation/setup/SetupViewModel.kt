package com.joo.miruni.presentation.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joo.miruni.domain.usecase.GetAlarmTimeUseCase
import com.joo.miruni.domain.usecase.SaveAlarmTimeUseCase
import com.joo.miruni.presentation.util.DateTimeFormatUtil
import com.joo.miruni.presentation.widget.Time
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class SetupViewModel @Inject constructor(
    private val saveAlarmTimeUseCase: SaveAlarmTimeUseCase,
    private val getAlarmTimeUseCase: GetAlarmTimeUseCase,
) : ViewModel() {

    // 선택된 시간
    private val _selectedTime = MutableStateFlow(LocalTime.of(8, 0))
    val selectedTime: StateFlow<LocalTime> = _selectedTime.asStateFlow()

    // Bool 시간 선택 진행 유뮤
    private val _showTimePicker = MutableStateFlow(false)
    val showTimePicker: StateFlow<Boolean> = _showTimePicker.asStateFlow()

    // 초기화 상태
    private val _isInit = MutableStateFlow(false)
    val isInit: StateFlow<Boolean> = _isInit.asStateFlow()

    init {
        getAlarmTime()
    }

    // 알람 시간 저장
    fun saveAlarmTime() {
        viewModelScope.launch {
            saveAlarmTimeUseCase(_selectedTime.value)
            _isInit.value = false
        }
    }

    // 알람 시간 있는지 확인
    private fun getAlarmTime() {
        viewModelScope.launch {
            val time = getAlarmTimeUseCase()

            _isInit.value = time == null

            time?.let {
                _selectedTime.value = it
            }
        }
    }

    // TimePicker 표시 on/off
    fun toggleTimePickerBtn() {
        _showTimePicker.value = !_showTimePicker.value
    }

    // 선택된 시간 업데이트 메서드
    fun updateSelectedTime(hour: Int, minute: Int, format: String) {
        _selectedTime.value = DateTimeFormatUtil.toLocalTime(hour, minute, format)
    }

    // 시간 Text 포멧
    fun formatLocalTimeToString(localTime: LocalTime): String {
        return DateTimeFormatUtil.formatLocalTimeToString(localTime)
    }

    // 시간 포멧
    fun convertLocalTimeToTime(localTime: LocalTime): Time {
        return DateTimeFormatUtil.convertLocalTimeToTime(localTime)
    }
}



