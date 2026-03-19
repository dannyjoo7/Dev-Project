package com.joo.miruni.domain.usecase.task.todo

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

class AddTodoItemUseCaseTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var useCase: AddTodoItemUseCase

    @Before
    fun setup() {
        taskRepository = mock()
        useCase = AddTodoItemUseCase(taskRepository)
    }

    @Test
    fun `invoke should convert TodoItem to TodoModel and call addTodo`() = runTest {
        val todoItem = TodoItem(
            id = 0L,
            title = "테스트 할 일",
            descriptionText = "설명",
            selectedDate = LocalDateTime.of(2026, 3, 20, 14, 0),
            adjustedDate = LocalDate.of(2026, 3, 19),
            isComplete = false,
            isPinned = false,
        )

        useCase.invoke(todoItem)

        verify(taskRepository).addTodo(argThat {
            title == "테스트 할 일" &&
                    details == "설명" &&
                    deadLine == LocalDateTime.of(2026, 3, 20, 14, 0) &&
                    alarmDisplayDate == LocalDate.of(2026, 3, 19) &&
                    !isComplete &&
                    !isPinned
        })
    }

    @Test
    fun `invoke should handle pinned todo item`() = runTest {
        val todoItem = TodoItem(
            id = 0L,
            title = "고정 할 일",
            descriptionText = "",
            selectedDate = LocalDateTime.of(2026, 3, 25, 9, 0),
            adjustedDate = LocalDate.of(2026, 3, 24),
            isComplete = false,
            isPinned = true,
        )

        useCase.invoke(todoItem)

        verify(taskRepository).addTodo(argThat { isPinned })
    }
}
