package com.joo.miruni.domain.usecase.task

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
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDate
import java.time.LocalDateTime

class GetTasksForDateRangeUseCaseTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var useCase: GetTasksForDateRangeUseCase

    @Before
    fun setup() {
        taskRepository = mock()
        useCase = GetTasksForDateRangeUseCase(taskRepository)
    }

    @Test
    fun `invoke should call repository and map entity to model`() = runTest {
        val startDate = LocalDate.of(2026, 3, 1)
        val endDate = LocalDate.of(2026, 3, 31)

        val taskEntity = TaskEntity(
            id = 1L,
            title = "Test Task",
            details = "Details",
            startDate = null,
            endDate = null,
            deadLine = LocalDateTime.of(2026, 3, 15, 10, 0),
            alarmDisplayDate = LocalDate.of(2026, 3, 14),
            isComplete = false,
            completeDate = null,
            type = TaskType.TODO,
            isPinned = false,
        )
        val taskItemsEntity = TaskItemsEntity(taskItemsEntity = listOf(taskEntity))

        whenever(taskRepository.getTasksForDateRange(startDate, endDate))
            .thenReturn(flowOf(taskItemsEntity))

        val result = useCase.invoke(startDate, endDate).first()

        verify(taskRepository).getTasksForDateRange(startDate, endDate)
        assertEquals(1, result.taskEntities.size)
        assertEquals("Test Task", result.taskEntities[0].title)
        assertEquals(1L, result.taskEntities[0].id)
    }

    @Test
    fun `invoke should return empty list when repository returns empty entity`() = runTest {
        val startDate = LocalDate.of(2026, 1, 1)
        val endDate = LocalDate.of(2026, 1, 31)

        val taskItemsEntity = TaskItemsEntity(taskItemsEntity = emptyList())

        whenever(taskRepository.getTasksForDateRange(startDate, endDate))
            .thenReturn(flowOf(taskItemsEntity))

        val result = useCase.invoke(startDate, endDate).first()

        assertEquals(0, result.taskEntities.size)
    }
}
