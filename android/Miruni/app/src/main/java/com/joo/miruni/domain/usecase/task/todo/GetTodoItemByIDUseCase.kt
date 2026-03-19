package com.joo.miruni.domain.usecase.task.todo

import com.joo.miruni.domain.model.TodoModel
import com.joo.miruni.domain.model.toTodoModel
import com.joo.miruni.domain.repository.TaskRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTodoItemByIDUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(
        taskId: Long,
    ): TodoModel {
        return taskRepository.getTaskItemById(taskId).toTodoModel()
    }
}
