package com.joo.miruni.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.joo.miruni.R
import com.joo.miruni.domain.usecase.setting.SettingActiveUnlockScreenUseCase
import com.joo.miruni.domain.usecase.setting.SettingCompletedItemsVisibilityUseCase
import com.joo.miruni.domain.usecase.setting.SettingGetCompletedItemsVisibilityStateUseCase
import com.joo.miruni.domain.usecase.setting.SettingGetUnlockStateUseCase
import com.joo.miruni.presentation.BottomNavItem
import com.joo.miruni.presentation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingCompletedItemsVisibilityUseCase: SettingCompletedItemsVisibilityUseCase,
    private val settingActiveUnlockScreenUseCase: SettingActiveUnlockScreenUseCase,
    private val settingGetCompletedItemsVisibilityStateUseCase: SettingGetCompletedItemsVisibilityStateUseCase,
    private val settingGetUnlockStateUseCase: SettingGetUnlockStateUseCase,
) : ViewModel() {

    companion object {
        const val TAG = "MainViewModel"
    }

    /*
    * State Flow
    * */

    // 완료 항목 값
    private val _settingObserveCompleteVisibility = MutableStateFlow(false)
    val settingObserveCompleteVisibility: StateFlow<Boolean> = _settingObserveCompleteVisibility.asStateFlow()

    // 잠금화면 활성화 여부
    private val _settingObserveUnlockState = MutableStateFlow(true)
    val settingObserveUnlockState: StateFlow<Boolean> = _settingObserveUnlockState.asStateFlow()

    init {
        loadUserSetting()
    }

    // 아이콘 리소스 ID만 저장
    val bottomNavItems: List<BottomNavItem> = listOf(
        BottomNavItem(R.string.nav_overdue, R.drawable.ic_clock, Screen.Overdue),
        BottomNavItem(R.string.nav_home, R.drawable.ic_home, Screen.Home),
        BottomNavItem(R.string.nav_calendar, R.drawable.ic_calendar, Screen.Calendar)
    )

    // 완료된 항목 보기 설정
    fun setCompletedItemsVisibility() {
        viewModelScope.launch {
            runCatching {
                settingCompletedItemsVisibilityUseCase.invoke()
            }.onSuccess {
                _settingObserveCompleteVisibility.value =
                    !_settingObserveCompleteVisibility.value
            }.onFailure { exception ->
                Log.e(TAG, "Failed to load settings", exception)
            }
        }
    }

    // 잠금 화면 활성화 / 비활성화 기능
    fun setActiveUnlockScreen() {
        viewModelScope.launch {
            runCatching {
                settingActiveUnlockScreenUseCase.invoke()
            }.onSuccess {
                _settingObserveUnlockState.value =
                    !_settingObserveUnlockState.value
            }.onFailure { exception ->
                Log.e(TAG, "Failed to load settings", exception)
            }
        }
    }

    // 유저 설정 로드
    private fun loadUserSetting() {
        viewModelScope.launch {
            // 완료된 아이템 가시성 상태 로드
            runCatching {
                settingGetCompletedItemsVisibilityStateUseCase.invoke()
            }.onSuccess { visibility ->
                _settingObserveCompleteVisibility.value = visibility
            }.onFailure { exception ->
                Log.e(TAG, "Failed to load completed items visibility", exception)
            }

            // 잠금 상태 로드
            runCatching {
                settingGetUnlockStateUseCase.invoke()
            }.onSuccess { unlockState ->
                _settingObserveUnlockState.value = unlockState
            }.onFailure { exception ->
                Log.e(TAG, "Failed to load unlock state", exception)
            }
        }
    }
}



