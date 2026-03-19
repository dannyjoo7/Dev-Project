package com.joo.miruni.domain.model

import com.joo.miruni.data.entities.TaskEntity
import com.joo.miruni.data.entities.TaskItemsEntity
import com.joo.miruni.data.entities.TaskType
import com.joo.miruni.presentation.addTask.addTodo.TodoItem
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

class TodoMapperTest {

    @Test
    fun `TaskEntity toTodoModel should map all fields correctly`() {
        val entity = TaskEntity(
            id = 1L,
            title = "테스트",
            details = "설명 텍스트",
            startDate = null,
            endDate = null,
            deadLine = LocalDateTime.of(2026, 3, 20, 14, 0),
            alarmDisplayDate = LocalDate.of(2026, 3, 19),
            isComplete = false,
            completeDate = null,
            type = TaskType.TODO,
            isPinned = true,
        )

        val model = entity.toTodoModel()

        assertEquals(1L, model.id)
        assertEquals("테스트", model.title)
        assertEquals("설명 텍스트", model.details)
        assertEquals(LocalDateTime.of(2026, 3, 20, 14, 0), model.deadLine)
        assertEquals(LocalDate.of(2026, 3, 19), model.alarmDisplayDate)
        assertEquals(false, model.isComplete)
        assertNull(model.completeDate)
        assertEquals(TaskType.TODO, model.type)
        assertEquals(true, model.isPinned)
    }

    @Test
    fun `TaskEntity toTodoModel should use today when alarmDisplayDate is null`() {
        val entity = TaskEntity(
            id = 1L,
            title = "테스트",
            details = null,
            startDate = null,
            endDate = null,
            deadLine = null,
            alarmDisplayDate = null,
            isComplete = false,
            completeDate = null,
            type = TaskType.TODO,
            isPinned = false,
        )

        val model = entity.toTodoModel()

        assertEquals(LocalDate.now(), model.alarmDisplayDate)
    }

    @Test
    fun `TodoModel toTaskEntity should map correctly with null startDate and endDate`() {
        val model = TodoModel(
            id = 2L,
            title = "할 일",
            details = "디테일",
            deadLine = LocalDateTime.of(2026, 4, 1, 9, 0),
            alarmDisplayDate = LocalDate.of(2026, 3, 30),
            type = TaskType.TODO,
            isComplete = true,
            completeDate = LocalDateTime.of(2026, 3, 31, 8, 0),
            isPinned = false,
        )

        val entity = model.toTaskEntity()

        assertEquals(2L, entity.id)
        assertEquals("할 일", entity.title)
        assertNull(entity.startDate)
        assertNull(entity.endDate)
        assertEquals(LocalDateTime.of(2026, 4, 1, 9, 0), entity.deadLine)
        assertEquals(true, entity.isComplete)
    }

    @Test
    fun `TaskItemsEntity toTodoItemsModel should map list correctly`() {
        val entities = TaskItemsEntity(
            listOf(
                TaskEntity(1L, "A", null, null, null, null, null, false, null, TaskType.TODO, false),
                TaskEntity(2L, "B", null, null, null, null, null, true, null, TaskType.TODO, true),
            )
        )

        val result = entities.toTodoItemsModel()

        assertEquals(2, result.todoEntities.size)
        assertEquals("A", result.todoEntities[0].title)
        assertEquals("B", result.todoEntities[1].title)
        assertEquals(true, result.todoEntities[1].isPinned)
    }

    @Test
    fun `TodoItem toTodoModel should set isComplete false and type TODO`() {
        val todoItem = TodoItem(
            id = 0L,
            title = "새 할 일",
            descriptionText = "메모",
            selectedDate = LocalDateTime.of(2026, 5, 1, 12, 0),
            adjustedDate = LocalDate.of(2026, 4, 30),
            isComplete = false,
            isPinned = true,
        )

        val model = todoItem.toTodoModel()

        assertEquals("새 할 일", model.title)
        assertEquals("메모", model.details)
        assertEquals(false, model.isComplete)
        assertNull(model.completeDate)
        assertEquals(TaskType.TODO, model.type)
        assertEquals(true, model.isPinned)
    }
}
