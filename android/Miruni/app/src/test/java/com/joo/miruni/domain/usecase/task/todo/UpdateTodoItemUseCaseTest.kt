package com.joo.miruni.domain.usecase.task.todo

import com.joo.miruni.data.entities.TaskType
import com.joo.miruni.domain.repository.TaskRepository
import com.joo.miruni.presentation.addTask.addTodo.TodoItem
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.argThat
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import java.time.LocalDate
import java.time.LocalDateTime

class UpdateTodoItemUseCaseTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var useCase: UpdateTodoItemUseCase

    @Before
    fun setup() {
        taskRepository = mock()
        useCase = UpdateTodoItemUseCase(taskRepository)
    }

    @Test
    fun `invoke should convert TodoItem to TaskEntity and call updateTask`() = runTest {
        val todoItem = TodoItem(
            id = 5L,
            title = "수정 할 일",
            descriptionText = "수정된 설명",
            selectedDate = LocalDateTime.of(2026, 3, 25, 9, 0),
            adjustedDate = LocalDate.of(2026, 3, 24),
            isComplete = false,
            isPinned = true,
        )

        useCase.invoke(todoItem)

        verify(taskRepository).updateTask(argThat {
            id == 5L &&
                    title == "수정 할 일" &&
                    details == "수정된 설명" &&
                    deadLine == LocalDateTime.of(2026, 3, 25, 9, 0) &&
                    alarmDisplayDate == LocalDate.of(2026, 3, 24) &&
                    type == TaskType.TODO &&
                    !isComplete &&
                    isPinned
        })
    }
}
