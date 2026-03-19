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

class AddScheduleItemUseCaseTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var useCase: AddScheduleItemUseCase

    @Before
    fun setup() {
        taskRepository = mock()
        useCase = AddScheduleItemUseCase(taskRepository)
    }

    @Test
    fun `invoke should convert ScheduleItem to ScheduleModel and call addSchedule`() = runTest {
        val scheduleItem = ScheduleItem(
            id = 0L,
            title = "테스트 일정",
            descriptionText = "설명",
            startDate = LocalDate.of(2026, 3, 20),
            endDate = LocalDate.of(2026, 3, 22),
            isComplete = false,
            isPinned = false,
        )

        useCase.invoke(scheduleItem)

        verify(taskRepository).addSchedule(argThat {
            title == "테스트 일정" &&
                    details == "설명" &&
                    startDate == LocalDate.of(2026, 3, 20) &&
                    endDate == LocalDate.of(2026, 3, 22) &&
                    type == TaskType.SCHEDULE &&
                    !isComplete &&
                    !isPinned &&
                    alarmDisplayDate == null &&
                    completeDate == null
        })
    }

    @Test
    fun `invoke should handle pinned schedule item`() = runTest {
        val scheduleItem = ScheduleItem(
            id = 0L,
            title = "고정 일정",
            descriptionText = "",
            startDate = LocalDate.of(2026, 3, 25),
            endDate = LocalDate.of(2026, 3, 26),
            isComplete = false,
            isPinned = true,
        )

        useCase.invoke(scheduleItem)

        verify(taskRepository).addSchedule(argThat { isPinned })
    }
}
