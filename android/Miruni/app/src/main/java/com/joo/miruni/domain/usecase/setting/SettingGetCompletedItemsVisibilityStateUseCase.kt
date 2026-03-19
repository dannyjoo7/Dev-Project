package com.joo.miruni.domain.usecase.setting

import com.joo.miruni.domain.repository.SharedPreferenceRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingGetCompletedItemsVisibilityStateUseCase @Inject constructor(
    private val sharedPreferenceRepository: SharedPreferenceRepository,
) {
    operator fun invoke(): Boolean {
        return sharedPreferenceRepository.getSettingCompletedItemsVisibilityState()
    }
}
