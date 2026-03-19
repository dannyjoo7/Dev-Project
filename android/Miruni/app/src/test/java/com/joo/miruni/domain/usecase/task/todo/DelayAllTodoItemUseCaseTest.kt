package com.joo.miruni.domain.usecase.task.todo

import com.joo.miruni.domain.repository.TaskRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.LocalDateTime

class DelayAllTodoItemUseCaseTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var useCase: DelayAllTodoItemUseCase

    @Before
    fun setup() {
        taskRepository = mock()
        useCase = DelayAllTodoItemUseCase(taskRepository)
    }

    @Test
    fun `invoke should call delayAllTodoEntity with correct params`() = runTest {
        val itemIds = listOf(1L, 2L, 3L)
        val delayDateTime = LocalDateTime.of(2026, 3, 20, 14, 0)

        useCase.invoke(itemIds, delayDateTime)

        verify(taskRepository).delayAllTodoEntity(itemIds, delayDateTime)
    }
}
