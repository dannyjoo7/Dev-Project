package com.joo.miruni.domain.usecase.task

import com.joo.miruni.domain.repository.TaskRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class CancelCompleteTaskItemUseCaseTest {
    private lateinit var taskRepository: TaskRepository
    private lateinit var useCase: CancelCompleteTaskItemUseCase

    @Before
    fun setup() {
        taskRepository = mock()
        useCase = CancelCompleteTaskItemUseCase(taskRepository)
    }

    @Test
    fun `invoke should call markTaskAsCancelCompleted with correct id`() = runTest {
        useCase.invoke(5L)
        verify(taskRepository).markTaskAsCancelCompleted(5L)
    }
}
