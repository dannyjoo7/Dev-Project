package com.joo.miruni.domain.usecase

import com.joo.miruni.domain.repository.TaskRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke() {
        return taskRepository.test()
    }
}
