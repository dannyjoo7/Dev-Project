package com.joo.miruni.domain.usecase.task

import com.joo.miruni.domain.repository.TaskRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CancelCompleteTaskItemUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(id: Long) {
        taskRepository.markTaskAsCancelCompleted(id)
    }
}
