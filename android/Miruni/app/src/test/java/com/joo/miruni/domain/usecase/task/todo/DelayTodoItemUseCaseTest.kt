package com.joo.miruni.domain.usecase.task.todo

import com.joo.miruni.domain.repository.TaskRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.LocalDateTime

class DelayTodoItemUseCaseTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var useCase: DelayTodoItemUseCase

    @Before
    fun setup() {
        taskRepository = mock()
        useCase = DelayTodoItemUseCase(taskRepository)
    }

    @Test
    fun `invoke should call delayTodoEntity with correct params`() = runTest {
        val id = 1L
        val delayDateTime = LocalDateTime.of(2026, 3, 20, 14, 0)

        useCase.invoke(id, delayDateTime)

        verify(taskRepository).delayTodoEntity(id, delayDateTime)
    }
}
