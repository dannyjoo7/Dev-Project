package com.joo.miruni.domain.usecase.task.todo

import com.joo.miruni.domain.model.toTodoModel
import com.joo.miruni.domain.repository.TaskRepository
import com.joo.miruni.presentation.addTask.addTodo.TodoItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddTodoItemUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(todoItem: TodoItem) {
        taskRepository.addTodo(todoItem.toTodoModel())
    }
}
