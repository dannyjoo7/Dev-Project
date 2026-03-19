package com.joo.miruni.presentation.util

import com.joo.miruni.data.entities.TaskType
import com.joo.miruni.domain.model.ScheduleModel
import com.joo.miruni.domain.model.TodoModel
import com.joo.miruni.presentation.home.Schedule
import com.joo.miruni.presentation.home.ThingsTodo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

class TaskLoadingHelperTest {

    // mapToThingsTodo

    @Test
    fun `mapToThingsTodo should map all fields correctly`() {
        val deadline = LocalDateTime.of(2026, 3, 20, 10, 0)
        val completeDate = LocalDateTime.of(2026, 3, 19, 15, 0)
        val todoModel = TodoModel(
            id = 1L,
            title = "Test Todo",
            details = "Test details",
            deadLine = deadline,
            alarmDisplayDate = LocalDate.of(2026, 3, 19),
            type = TaskType.TODO,
            isComplete = true,
            completeDate = completeDate,
            isPinned = false,
        )

        val result = TaskLoadingHelper.mapToThingsTodo(todoModel)

        assertEquals(1L, result.id)
        assertEquals("Test Todo", result.title)
        assertEquals(deadline, result.deadline)
        assertEquals("Test details", result.description)
        assertEquals(true, result.isCompleted)
        assertEquals(completeDate, result.completeDate)
        assertEquals(false, result.isPinned)
    }

    @Test
    fun `mapToThingsTodo should use now when deadline is null`() {
        val before = LocalDateTime.now()
        val todoModel = TodoModel(
            id = 2L,
            title = "No Deadline",
            details = null,
            deadLine = null,
            alarmDisplayDate = LocalDate.of(2026, 3, 19),
            type = TaskType.TODO,
            isComplete = false,
            completeDate = null,
            isPinned = true,
        )

        val result = TaskLoadingHelper.mapToThingsTodo(todoModel)
        val after = LocalDateTime.now()

        assertEquals(2L, result.id)
        assertEquals("No Deadline", result.title)
        assertEquals("", result.description)
        assertEquals(false, result.isCompleted)
        assertEquals(null, result.completeDate)
        assertEquals(true, result.isPinned)
        // deadline should be approximately now
        assertTrue(
            result.deadline >= before.withNano(0) && result.deadline <= after.plusSeconds(1)
        )
    }

    // mapToSchedule

    @Test
    fun `mapToSchedule should map all fields correctly`() {
        val today = LocalDate.now()
        val startDate = today.plusDays(3)
        val endDate = today.plusDays(5)
        val completeDate = LocalDateTime.of(2026, 3, 19, 12, 0)
        val scheduleModel = ScheduleModel(
            id = 10L,
            title = "Test Schedule",
            details = "Schedule details",
            startDate = startDate,
            endDate = endDate,
            alarmDisplayDate = today,
            isComplete = false,
            completeDate = completeDate,
            type = TaskType.SCHEDULE,
            isPinned = true,
        )

        val result = TaskLoadingHelper.mapToSchedule(scheduleModel)

        assertEquals(10L, result.id)
        assertEquals("Test Schedule", result.title)
        assertEquals(startDate, result.startDate)
        assertEquals(endDate, result.endDate)
        assertEquals("Schedule details", result.description)
        assertEquals(3, result.daysBefore)
        assertEquals(false, result.isComplete)
        assertEquals(completeDate, result.completeDate)
        assertEquals(true, result.isPinned)
    }

    // calculateDaysBefore

    @Test
    fun `calculateDaysBefore should return 0 when startDate is today`() {
        val today = LocalDate.now()
        assertEquals(0, TaskLoadingHelper.calculateDaysBefore(today, today.plusDays(3)))
    }

    @Test
    fun `calculateDaysBefore should return days until future startDate`() {
        val today = LocalDate.now()
        val futureStart = today.plusDays(5)
        assertEquals(5, TaskLoadingHelper.calculateDaysBefore(futureStart, futureStart.plusDays(2)))
    }

    @Test
    fun `calculateDaysBefore should return 0 when in middle of event range`() {
        val today = LocalDate.now()
        val pastStart = today.minusDays(2)
        val futureEnd = today.plusDays(2)
        assertEquals(0, TaskLoadingHelper.calculateDaysBefore(pastStart, futureEnd))
    }

    @Test
    fun `calculateDaysBefore should return 0 when startDate is null`() {
        assertEquals(0, TaskLoadingHelper.calculateDaysBefore(null, null))
    }

    @Test
    fun `calculateDaysBefore should return 0 when past start and past end`() {
        val today = LocalDate.now()
        val pastStart = today.minusDays(5)
        val pastEnd = today.minusDays(1)
        assertEquals(0, TaskLoadingHelper.calculateDaysBefore(pastStart, pastEnd))
    }

    @Test
    fun `calculateDaysBefore should return 1 for tomorrow start`() {
        val tomorrow = LocalDate.now().plusDays(1)
        assertEquals(1, TaskLoadingHelper.calculateDaysBefore(tomorrow, tomorrow.plusDays(3)))
    }

    // sortTodoItems

    @Test
    fun `sortTodoItems should remove duplicates by id`() {
        val deadline = LocalDateTime.of(2026, 3, 20, 10, 0)
        val items = listOf(
            ThingsTodo(id = 1L, title = "A", deadline = deadline, isCompleted = false, completeDate = null, isPinned = false),
            ThingsTodo(id = 1L, title = "A duplicate", deadline = deadline, isCompleted = false, completeDate = null, isPinned = false),
            ThingsTodo(id = 2L, title = "B", deadline = deadline, isCompleted = false, completeDate = null, isPinned = false),
        )

        val result = TaskLoadingHelper.sortTodoItems(items)

        assertEquals(2, result.size)
        assertEquals(1L, result[0].id)
        assertEquals(2L, result[1].id)
    }

    @Test
    fun `sortTodoItems should sort pinned items first`() {
        val deadline1 = LocalDateTime.of(2026, 3, 20, 10, 0)
        val deadline2 = LocalDateTime.of(2026, 3, 19, 10, 0)
        val items = listOf(
            ThingsTodo(id = 1L, title = "Not pinned", deadline = deadline1, isCompleted = false, completeDate = null, isPinned = false),
            ThingsTodo(id = 2L, title = "Pinned", deadline = deadline2, isCompleted = false, completeDate = null, isPinned = true),
        )

        val result = TaskLoadingHelper.sortTodoItems(items)

        assertEquals(2L, result[0].id)
        assertEquals(1L, result[1].id)
    }

    @Test
    fun `sortTodoItems should sort by deadline within same pin status`() {
        val earlier = LocalDateTime.of(2026, 3, 18, 10, 0)
        val later = LocalDateTime.of(2026, 3, 25, 10, 0)
        val items = listOf(
            ThingsTodo(id = 1L, title = "Later", deadline = later, isCompleted = false, completeDate = null, isPinned = false),
            ThingsTodo(id = 2L, title = "Earlier", deadline = earlier, isCompleted = false, completeDate = null, isPinned = false),
        )

        val result = TaskLoadingHelper.sortTodoItems(items)

        assertEquals(2L, result[0].id)
        assertEquals(1L, result[1].id)
    }

    // sortScheduleItems

    @Test
    fun `sortScheduleItems should sort pinned items first`() {
        val items = listOf(
            Schedule(id = 1L, title = "Not pinned", startDate = LocalDate.of(2026, 3, 18), endDate = LocalDate.of(2026, 3, 20), daysBefore = 0, isComplete = false, completeDate = null, isPinned = false),
            Schedule(id = 2L, title = "Pinned", startDate = LocalDate.of(2026, 3, 25), endDate = LocalDate.of(2026, 3, 27), daysBefore = 0, isComplete = false, completeDate = null, isPinned = true),
        )

        val result = TaskLoadingHelper.sortScheduleItems(items)

        assertEquals(2L, result[0].id)
        assertEquals(1L, result[1].id)
    }

    @Test
    fun `sortScheduleItems should sort by startDate within same pin status`() {
        val items = listOf(
            Schedule(id = 1L, title = "Later", startDate = LocalDate.of(2026, 3, 25), endDate = LocalDate.of(2026, 3, 27), daysBefore = 0, isComplete = false, completeDate = null, isPinned = false),
            Schedule(id = 2L, title = "Earlier", startDate = LocalDate.of(2026, 3, 18), endDate = LocalDate.of(2026, 3, 20), daysBefore = 0, isComplete = false, completeDate = null, isPinned = false),
        )

        val result = TaskLoadingHelper.sortScheduleItems(items)

        assertEquals(2L, result[0].id)
        assertEquals(1L, result[1].id)
    }

    @Test
    fun `sortScheduleItems should handle null startDate`() {
        val items = listOf(
            Schedule(id = 1L, title = "Has date", startDate = LocalDate.of(2026, 3, 20), endDate = LocalDate.of(2026, 3, 22), daysBefore = 0, isComplete = false, completeDate = null, isPinned = false),
            Schedule(id = 2L, title = "No date", startDate = null, endDate = null, daysBefore = 0, isComplete = false, completeDate = null, isPinned = false),
        )

        val result = TaskLoadingHelper.sortScheduleItems(items)

        // null sorts first with thenBy
        assertEquals(2, result.size)
    }
}
