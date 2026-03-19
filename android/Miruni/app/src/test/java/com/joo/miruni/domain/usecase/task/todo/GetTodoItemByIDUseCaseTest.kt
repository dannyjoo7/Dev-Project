package com.joo.miruni.domain.usecase.task.todo

import com.joo.miruni.data.entities.TaskEntity
import com.joo.miruni.data.entities.TaskType
import com.joo.miruni.domain.repository.TaskRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalDate
import java.time.LocalDateTime

class GetTodoItemByIDUseCaseTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var useCase: GetTodoItemByIDUseCase

    @Before
    fun setup() {
        taskRepository = mock()
        useCase = GetTodoItemByIDUseCase(taskRepository)
    }

    @Test
    fun `invoke should return TodoModel mapped from repository TaskEntity`() = runTest {
        val taskId = 1L
        val taskEntity = TaskEntity(
            id = taskId,
            title = "할 일 조회",
            details = "설명 텍스트",
            startDate = null,
            endDate = null,
            deadLine = LocalDateTime.of(2026, 3, 20, 14, 0),
            alarmDisplayDate = LocalDate.of(2026, 3, 19),
            isComplete = false,
            completeDate = null,
            type = TaskType.TODO,
            isPinned = false,
        )
        whenever(taskRepository.getTaskItemById(taskId)).thenReturn(taskEntity)

        val result = useCase.invoke(taskId)

        assertEquals(taskId, result.id)
        assertEquals("할 일 조회", result.title)
        assertEquals("설명 텍스트", result.details)
        assertEquals(LocalDateTime.of(2026, 3, 20, 14, 0), result.deadLine)
        assertEquals(LocalDate.of(2026, 3, 19), result.alarmDisplayDate)
        assertEquals(TaskType.TODO, result.type)
        assertEquals(false, result.isComplete)
        assertEquals(false, result.isPinned)
    }
}
