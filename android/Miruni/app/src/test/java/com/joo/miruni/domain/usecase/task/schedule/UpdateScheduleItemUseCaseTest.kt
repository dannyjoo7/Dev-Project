package com.joo.miruni.domain.usecase.task.schedule

import com.joo.miruni.data.entities.TaskType
import com.joo.miruni.domain.repository.TaskRepository
import com.joo.miruni.presentation.addTask.addSchedule.ScheduleItem
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.argThat
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.LocalDate

class UpdateScheduleItemUseCaseTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var useCase: UpdateScheduleItemUseCase

    @Before
    fun setup() {
        taskRepository = mock()
        useCase = UpdateScheduleItemUseCase(taskRepository)
    }

    @Test
    fun `invoke should convert ScheduleItem to TaskEntity and call updateTask`() = runTest {
        val scheduleItem = ScheduleItem(
            id = 5L,
            title = "수정된 일정",
            descriptionText = "수정된 설명",
            startDate = LocalDate.of(2026, 4, 1),
            endDate = LocalDate.of(2026, 4, 3),
            isComplete = false,
            isPinned = false,
        )

        useCase.invoke(scheduleItem)

        verify(taskRepository).updateTask(argThat {
            id == 5L &&
                    title == "수정된 일정" &&
                    details == "수정된 설명" &&
                    startDate == LocalDate.of(2026, 4, 1) &&
                    endDate == LocalDate.of(2026, 4, 3) &&
                    deadLine == null &&
                    alarmDisplayDate == null &&
                    type == TaskType.SCHEDULE &&
                    !isComplete &&
                    !isPinned &&
                    completeDate == null
        })
    }

    @Test
    fun `invoke should handle pinned schedule item update`() = runTest {
        val scheduleItem = ScheduleItem(
            id = 10L,
            title = "고정 일정 수정",
            descriptionText = "",
            startDate = LocalDate.of(2026, 4, 5),
            endDate = LocalDate.of(2026, 4, 6),
            isComplete = false,
            isPinned = true,
        )

        useCase.invoke(scheduleItem)

        verify(taskRepository).updateTask(argThat {
            id == 10L && isPinned
        })
    }
}
