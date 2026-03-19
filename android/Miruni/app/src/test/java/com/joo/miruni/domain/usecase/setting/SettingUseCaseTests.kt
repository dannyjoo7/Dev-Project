package com.joo.miruni.domain.usecase.setting

import com.joo.miruni.domain.repository.SharedPreferenceRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SettingUseCaseTests {
    private lateinit var repository: SharedPreferenceRepository

    @Before
    fun setup() {
        repository = mock()
    }

    // SettingActiveUnlockScreenUseCase

    @Test
    fun `SettingActiveUnlockScreen should call repository`() = runTest {
        val useCase = SettingActiveUnlockScreenUseCase(repository)
        useCase.invoke()
        verify(repository).settingActiveUnlockScreen()
    }

    // SettingCompletedItemsVisibilityUseCase

    @Test
    fun `SettingCompletedItemsVisibility should call repository`() = runTest {
        val useCase = SettingCompletedItemsVisibilityUseCase(repository)
        useCase.invoke()
        verify(repository).settingUpdateCompletedItemsVisibility()
    }

    // SettingGetCompletedItemsVisibilityStateUseCase

    @Test
    fun `SettingGetCompletedItemsVisibilityState should return true from repository`() {
        whenever(repository.getSettingCompletedItemsVisibilityState()).thenReturn(true)
        val useCase = SettingGetCompletedItemsVisibilityStateUseCase(repository)
        assertEquals(true, useCase.invoke())
    }

    @Test
    fun `SettingGetCompletedItemsVisibilityState should return false from repository`() {
        whenever(repository.getSettingCompletedItemsVisibilityState()).thenReturn(false)
        val useCase = SettingGetCompletedItemsVisibilityStateUseCase(repository)
        assertEquals(false, useCase.invoke())
    }

    // SettingGetUnlockStateUseCase

    @Test
    fun `SettingGetUnlockState should return true from repository`() {
        whenever(repository.getSettingUnlockScreenState()).thenReturn(true)
        val useCase = SettingGetUnlockStateUseCase(repository)
        assertEquals(true, useCase.invoke())
    }

    @Test
    fun `SettingGetUnlockState should return false from repository`() {
        whenever(repository.getSettingUnlockScreenState()).thenReturn(false)
        val useCase = SettingGetUnlockStateUseCase(repository)
        assertEquals(false, useCase.invoke())
    }

    // SettingObserveCompletedItemsVisibilityUseCase

    @Test
    fun `SettingObserveCompletedItemsVisibility should return flow from repository`() = runTest {
        whenever(repository.observeSettingCompletedItemsVisibility()).thenReturn(flowOf(true))
        val useCase = SettingObserveCompletedItemsVisibilityUseCase(repository)
        assertEquals(true, useCase.invoke().first())
    }

    @Test
    fun `SettingObserveCompletedItemsVisibility should return false flow from repository`() = runTest {
        whenever(repository.observeSettingCompletedItemsVisibility()).thenReturn(flowOf(false))
        val useCase = SettingObserveCompletedItemsVisibilityUseCase(repository)
        assertEquals(false, useCase.invoke().first())
    }

    // SettingObserveUnlockStateUseCase

    @Test
    fun `SettingObserveUnlockState should return flow from repository`() = runTest {
        whenever(repository.observeSettingUnlockState()).thenReturn(flowOf(true))
        val useCase = SettingObserveUnlockStateUseCase(repository)
        assertEquals(true, useCase.invoke().first())
    }

    @Test
    fun `SettingObserveUnlockState should return false flow from repository`() = runTest {
        whenever(repository.observeSettingUnlockState()).thenReturn(flowOf(false))
        val useCase = SettingObserveUnlockStateUseCase(repository)
        assertEquals(false, useCase.invoke().first())
    }
}
