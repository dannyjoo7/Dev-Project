package com.joo.miruni.domain.usecase.task.schedule

import com.joo.miruni.domain.model.toScheduleEntity
import com.joo.miruni.domain.repository.TaskRepository
import com.joo.miruni.presentation.addTask.addSchedule.ScheduleItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddScheduleItemUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(scheduleItem: ScheduleItem) {
        taskRepository.addSchedule(scheduleItem.toScheduleEntity())
    }
}
