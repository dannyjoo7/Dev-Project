package com.joo.miruni.domain.usecase.task

import com.joo.miruni.domain.repository.TaskRepository
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompleteTaskItemUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(id: Long, completionTime: LocalDateTime) {
        taskRepository.markTaskAsCompleted(id, completionTime)
    }
}
