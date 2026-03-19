package com.joo.miruni.domain.usecase.task.schedule

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

class GetScheduleItemsUseCaseTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var useCase: GetScheduleItemsUseCase

    @Before
    fun setup() {
        taskRepository = mock()
        useCase = GetScheduleItemsUseCase(taskRepository)
    }

    @Test
    fun `invoke should return mapped ScheduleItemsModel from repository flow`() = runTest {
        val selectDate = LocalDate.of(2026, 3, 19)
        val lastStartDate = LocalDate.of(2026, 3, 1)
        val taskEntity = TaskEntity(
            id = 1L,
            title = "일정 1",
            details = "설명",
            startDate = LocalDate.of(2026, 3, 19),
            endDate = LocalDate.of(2026, 3, 21),
            deadLine = null,
            alarmDisplayDate = null,
            isComplete = false,
            completeDate = null,
            type = TaskType.SCHEDULE,
            isPinned = false,
        )
        val taskItemsEntity = TaskItemsEntity(listOf(taskEntity))
        whenever(taskRepository.getSchedules(selectDate, lastStartDate))
            .thenReturn(flowOf(taskItemsEntity))

        val result = useCase.invoke(selectDate, lastStartDate).first()

        assertEquals(1, result.scheduleEntities.size)
        assertEquals("일정 1", result.scheduleEntities[0].title)
        assertEquals(TaskType.SCHEDULE, result.scheduleEntities[0].type)
        assertEquals(LocalDate.of(2026, 3, 19), result.scheduleEntities[0].startDate)
        assertEquals(LocalDate.of(2026, 3, 21), result.scheduleEntities[0].endDate)
    }

    @Test
    fun `invoke should return empty list when repository returns empty`() = runTest {
        val selectDate = LocalDate.of(2026, 3, 19)
        val lastStartDate: LocalDate? = null
        val emptyEntity = TaskItemsEntity(emptyList())
        whenever(taskRepository.getSchedules(selectDate, lastStartDate))
            .thenReturn(flowOf(emptyEntity))

        val result = useCase.invoke(selectDate, lastStartDate).first()

        assertEquals(0, result.scheduleEntities.size)
    }
}
