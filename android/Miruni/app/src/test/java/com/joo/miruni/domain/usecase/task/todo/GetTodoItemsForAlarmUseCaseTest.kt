package com.joo.miruni.domain.usecase.task.todo

import com.joo.miruni.data.entities.TaskEntity
import com.joo.miruni.data.entities.TaskItemsEntity
import com.joo.miruni.data.entities.TaskType
import com.joo.miruni.domain.repository.TaskRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalDate
import java.time.LocalDateTime

class GetTodoItemsForAlarmUseCaseTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var useCase: GetTodoItemsForAlarmUseCase

    @Before
    fun setup() {
        taskRepository = mock()
        useCase = GetTodoItemsForAlarmUseCase(taskRepository)
    }

    @Test
    fun `invoke should return mapped TodoItemsModel from repository flow`() = runTest {
        val selectDate = LocalDateTime.of(2026, 3, 19, 10, 0)
        val taskEntity = TaskEntity(
            id = 1L,
            title = "할 일 1",
            details = "설명",
            startDate = null,
            endDate = null,
            deadLine = LocalDateTime.of(2026, 3, 20, 14, 0),
            alarmDisplayDate = LocalDate.of(2026, 3, 19),
            isComplete = false,
            completeDate = null,
            type = TaskType.TODO,
            isPinned = false,
        )
        val taskItemsEntity = TaskItemsEntity(listOf(taskEntity))
        whenever(taskRepository.getTasksForAlarmByDate(selectDate))
            .thenReturn(flowOf(taskItemsEntity))

        val result = useCase.invoke(selectDate).first()

        assertEquals(1, result.todoEntities.size)
        assertEquals("할 일 1", result.todoEntities[0].title)
        assertEquals(TaskType.TODO, result.todoEntities[0].type)
    }

    @Test
    fun `invoke should return empty list when repository returns empty`() = runTest {
        val selectDate = LocalDateTime.of(2026, 3, 19, 10, 0)
        val emptyEntity = TaskItemsEntity(emptyList())
        whenever(taskRepository.getTasksForAlarmByDate(selectDate))
            .thenReturn(flowOf(emptyEntity))

        val result = useCase.invoke(selectDate).first()

        assertEquals(0, result.todoEntities.size)
    }
}
