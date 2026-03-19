package com.joo.miruni.domain.usecase

import com.joo.miruni.domain.repository.SharedPreferenceRepository
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveAlarmTimeUseCase @Inject constructor(
    private val sharedPreferenceRepository: SharedPreferenceRepository
) {
    suspend operator fun invoke(time: LocalTime) {
        sharedPreferenceRepository.saveAlarmTime(time)
    }
}
