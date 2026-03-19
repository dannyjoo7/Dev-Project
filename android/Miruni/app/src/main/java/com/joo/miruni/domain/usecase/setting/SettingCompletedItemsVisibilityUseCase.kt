package com.joo.miruni.domain.usecase.setting

import com.joo.miruni.domain.repository.SharedPreferenceRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingCompletedItemsVisibilityUseCase @Inject constructor(
    private val sharedPreferenceRepository: SharedPreferenceRepository,
) {
    suspend operator fun invoke() {
        sharedPreferenceRepository.settingUpdateCompletedItemsVisibility()
    }
}
