package com.joo.miruni.domain.usecase.task.todo

import com.joo.miruni.domain.repository.TaskRepository
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DelayTodoItemUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(id: Long, delayDateTime: LocalDateTime) {
        taskRepository.delayTodoEntity(id, delayDateTime)
    }
}
