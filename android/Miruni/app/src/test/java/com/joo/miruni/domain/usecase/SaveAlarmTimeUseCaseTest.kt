package com.joo.miruni.domain.usecase

import com.joo.miruni.domain.repository.SharedPreferenceRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.LocalTime

class SaveAlarmTimeUseCaseTest {

    private lateinit var sharedPreferenceRepository: SharedPreferenceRepository
    private lateinit var useCase: SaveAlarmTimeUseCase

    @Before
    fun setup() {
        sharedPreferenceRepository = mock()
        useCase = SaveAlarmTimeUseCase(sharedPreferenceRepository)
    }

    @Test
    fun `invoke should call saveAlarmTime with correct time`() = runTest {
        val time = LocalTime.of(9, 0)

        useCase.invoke(time)

        verify(sharedPreferenceRepository).saveAlarmTime(time)
    }
}
