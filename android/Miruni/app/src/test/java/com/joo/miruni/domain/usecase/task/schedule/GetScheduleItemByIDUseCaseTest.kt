package com.joo.miruni.domain.usecase.task.schedule

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

class GetScheduleItemByIDUseCaseTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var useCase: GetScheduleItemByIDUseCase

    @Before
    fun setup() {
        taskRepository = mock()
        useCase = GetScheduleItemByIDUseCase(taskRepository)
    }

    @Test
    fun `invoke should return ScheduleModel mapped from repository TaskEntity`() = runTest {
        val taskId = 1L
        val taskEntity = TaskEntity(
            id = taskId,
            title = "일정 조회",
            details = "세부사항",
            startDate = LocalDate.of(2026, 3, 20),
            endDate = LocalDate.of(2026, 3, 22),
            deadLine = null,
            alarmDisplayDate = null,
            isComplete = false,
            completeDate = null,
            type = TaskType.SCHEDULE,
            isPinned = false,
        )
        whenever(taskRepository.getTaskItemById(taskId))
            .thenReturn(taskEntity)

        val result = useCase.invoke(taskId)

        assertEquals(taskId, result.id)
        assertEquals("일정 조회", result.title)
        assertEquals("세부사항", result.details)
        assertEquals(LocalDate.of(2026, 3, 20), result.startDate)
        assertEquals(LocalDate.of(2026, 3, 22), result.endDate)
        assertEquals(TaskType.SCHEDULE, result.type)
        assertEquals(false, result.isComplete)
        assertEquals(false, result.isPinned)
    }
}
