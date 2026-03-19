package com.joo.miruni.domain.usecase.task

import com.joo.miruni.domain.repository.TaskRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.LocalDateTime

class CompleteTaskItemUseCaseTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var useCase: CompleteTaskItemUseCase

    @Before
    fun setup() {
        taskRepository = mock()
        useCase = CompleteTaskItemUseCase(taskRepository)
    }

    @Test
    fun `invoke should call markTaskAsCompleted with correct parameters`() = runTest {
        val id = 1L
        val completionTime = LocalDateTime.of(2026, 3, 19, 10, 0)

        useCase.invoke(id, completionTime)

        verify(taskRepository).markTaskAsCompleted(id, completionTime)
    }

    @Test
    fun `invoke should pass different ids correctly`() = runTest {
        val id = 999L
        val completionTime = LocalDateTime.of(2026, 1, 1, 0, 0)

        useCase.invoke(id, completionTime)

        verify(taskRepository).markTaskAsCompleted(id, completionTime)
    }
}
