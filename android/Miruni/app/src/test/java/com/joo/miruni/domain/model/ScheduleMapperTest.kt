package com.joo.miruni.domain.model

import com.joo.miruni.data.entities.TaskEntity
import com.joo.miruni.data.entities.TaskItemsEntity
import com.joo.miruni.data.entities.TaskType
import com.joo.miruni.presentation.addTask.addSchedule.ScheduleItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

class ScheduleMapperTest {

    @Test
    fun `TaskEntity toScheduleEntity should map all fields correctly`() {
        val entity = TaskEntity(
            id = 10L,
            title = "회의",
            details = "팀 회의",
            startDate = LocalDate.of(2026, 3, 20),
            endDate = LocalDate.of(2026, 3, 21),
            deadLine = null,
            alarmDisplayDate = LocalDate.of(2026, 3, 19),
            isComplete = false,
            completeDate = null,
            type = TaskType.SCHEDULE,
            isPinned = false,
        )

        val model = entity.toScheduleEntity()

        assertEquals(10L, model.id)
        assertEquals("회의", model.title)
        assertEquals("팀 회의", model.details)
        assertEquals(LocalDate.of(2026, 3, 20), model.startDate)
        assertEquals(LocalDate.of(2026, 3, 21), model.endDate)
        assertEquals(LocalDate.of(2026, 3, 19), model.alarmDisplayDate)
        assertEquals(false, model.isComplete)
        assertEquals(TaskType.SCHEDULE, model.type)
    }

    @Test
    fun `ScheduleModel toTaskEntity should set deadLine and alarmDisplayDate to null`() {
        val model = ScheduleModel(
            id = 5L,
            title = "일정",
            details = null,
            startDate = LocalDate.of(2026, 4, 1),
            endDate = LocalDate.of(2026, 4, 3),
            alarmDisplayDate = LocalDate.of(2026, 3, 31),
            isComplete = false,
            completeDate = null,
            type = TaskType.SCHEDULE,
            isPinned = true,
        )

        val entity = model.toTaskEntity()

        assertEquals(5L, entity.id)
        assertNull(entity.deadLine)
        assertNull(entity.alarmDisplayDate)
        assertEquals(LocalDate.of(2026, 4, 1), entity.startDate)
        assertEquals(true, entity.isPinned)
    }

    @Test
    fun `TaskItemsEntity toScheduleItemsEntity should map list correctly`() {
        val entities = TaskItemsEntity(
            listOf(
                TaskEntity(1L, "A", null, LocalDate.of(2026, 3, 1), LocalDate.of(2026, 3, 2), null, null, false, null, TaskType.SCHEDULE, false),
                TaskEntity(2L, "B", null, LocalDate.of(2026, 3, 5), null, null, null, false, null, TaskType.SCHEDULE, true),
            )
        )

        val result = entities.toScheduleItemsEntity()

        assertEquals(2, result.scheduleEntities.size)
        assertEquals("A", result.scheduleEntities[0].title)
        assertEquals(true, result.scheduleEntities[1].isPinned)
    }

    @Test
    fun `ScheduleItem toScheduleEntity should set defaults correctly`() {
        val item = ScheduleItem(
            id = 0L,
            title = "새 일정",
            descriptionText = "설명",
            startDate = LocalDate.of(2026, 6, 1),
            endDate = LocalDate.of(2026, 6, 5),
            isComplete = false,
            isPinned = false,
        )

        val model = item.toScheduleEntity()

        assertEquals("새 일정", model.title)
        assertEquals("설명", model.details)
        assertEquals(false, model.isComplete)
        assertNull(model.completeDate)
        assertNull(model.alarmDisplayDate)
        assertEquals(TaskType.SCHEDULE, model.type)
    }

    @Test
    fun `ScheduleModel round trip should preserve key fields`() {
        val original = ScheduleModel(
            id = 7L,
            title = "왕복",
            details = "테스트",
            startDate = LocalDate.of(2026, 5, 10),
            endDate = LocalDate.of(2026, 5, 12),
            alarmDisplayDate = LocalDate.of(2026, 5, 9),
            isComplete = true,
            completeDate = LocalDateTime.of(2026, 5, 11, 18, 0),
            type = TaskType.SCHEDULE,
            isPinned = true,
        )

        val entity = original.toTaskEntity()
        val restored = entity.toScheduleEntity()

        assertEquals(original.id, restored.id)
        assertEquals(original.title, restored.title)
        assertEquals(original.startDate, restored.startDate)
        assertEquals(original.endDate, restored.endDate)
        assertEquals(original.isComplete, restored.isComplete)
        assertEquals(original.isPinned, restored.isPinned)
    }
}
