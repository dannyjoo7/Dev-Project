package com.joo.miruni.domain.usecase

import com.joo.miruni.domain.repository.SharedPreferenceRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalTime

class GetAlarmTimeUseCaseTest {

    private lateinit var sharedPreferenceRepository: SharedPreferenceRepository
    private lateinit var useCase: GetAlarmTimeUseCase

    @Before
    fun setup() {
        sharedPreferenceRepository = mock()
        useCase = GetAlarmTimeUseCase(sharedPreferenceRepository)
    }

    @Test
    fun `invoke should return LocalTime from repository`() = runTest {
        val expectedTime = LocalTime.of(8, 30)
        whenever(sharedPreferenceRepository.getAlarmTime()).thenReturn(expectedTime)

        val result = useCase.invoke()

        assertEquals(expectedTime, result)
    }

    @Test
    fun `invoke should return null when repository returns null`() = runTest {
        whenever(sharedPreferenceRepository.getAlarmTime()).thenReturn(null)

        val result = useCase.invoke()

        assertNull(result)
    }
}
