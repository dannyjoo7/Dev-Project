package com.joo.miruni.domain.usecase.task

import com.joo.miruni.domain.repository.TaskRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class TogglePinStatusUseCaseTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var useCase: TogglePinStatusUseCase

    @Before
    fun setup() {
        taskRepository = mock()
        useCase = TogglePinStatusUseCase(taskRepository)
    }

    @Test
    fun `invoke should call togglePinStatus with correct id`() = runTest {
        val id = 10L

        useCase.invoke(id)

        verify(taskRepository).togglePinStatus(id)
    }
}
