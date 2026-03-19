package com.joo.miruni.domain.usecase.task

import com.joo.miruni.domain.repository.TaskRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteTaskItemUseCaseTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var useCase: DeleteTaskItemUseCase

    @Before
    fun setup() {
        taskRepository = mock()
        useCase = DeleteTaskItemUseCase(taskRepository)
    }

    @Test
    fun `invoke should call deleteTaskById with correct id`() = runTest {
        val id = 5L

        useCase.invoke(id)

        verify(taskRepository).deleteTaskById(id)
    }
}
