package com.joo.miruni.domain.usecase.setting

import com.joo.miruni.domain.repository.SharedPreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingObserveUnlockStateUseCase @Inject constructor(
    private val sharedPreferenceRepository: SharedPreferenceRepository,
) {
    suspend operator fun invoke(): Flow<Boolean> {
        return sharedPreferenceRepository.observeSettingUnlockState()
    }
}
