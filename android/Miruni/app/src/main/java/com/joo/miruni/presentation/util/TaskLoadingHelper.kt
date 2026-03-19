package com.joo.miruni.presentation.util

import com.joo.miruni.domain.model.ScheduleModel
import com.joo.miruni.domain.model.TodoModel
import com.joo.miruni.presentation.home.Schedule
import com.joo.miruni.presentation.home.ThingsTodo
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

object TaskLoadingHelper {

    fun mapToThingsTodo(todoModel: TodoModel): ThingsTodo {
        return ThingsTodo(
            id = todoModel.id,
            title = todoModel.title,
            deadline = todoModel.deadLine ?: LocalDateTime.now(),
            description = todoModel.details ?: "",
            isCompleted = todoModel.isComplete,
            completeDate = todoModel.completeDate,
            isPinned = todoModel.isPinned
        )
    }

    fun mapToSchedule(scheduleModel: ScheduleModel): Schedule {
        return Schedule(
            id = scheduleModel.id,
            title = scheduleModel.title,
            startDate = scheduleModel.startDate,
            endDate = scheduleModel.endDate,
            description = scheduleModel.details,
            daysBefore = calculateDaysBefore(scheduleModel.startDate, scheduleModel.endDate),
            isComplete = scheduleModel.isComplete,
            completeDate = scheduleModel.completeDate,
            isPinned = scheduleModel.isPinned
        )
    }

    fun calculateDaysBefore(startDate: LocalDate?, endDate: LocalDate?): Int {
        val today = LocalDate.now()
        return when {
            startDate != null && startDate.isEqual(today) -> 0
            startDate != null && endDate != null &&
                    startDate.isBefore(today) && endDate.isAfter(today) -> 0
            startDate != null && startDate.isAfter(today) ->
                ChronoUnit.DAYS.between(today, startDate).toInt()
            else -> 0
        }
    }

    fun sortTodoItems(items: List<ThingsTodo>): List<ThingsTodo> {
        return items.distinctBy { it.id }
            .sortedWith(compareByDescending<ThingsTodo> { it.isPinned }.thenBy { it.deadline })
    }

    fun sortScheduleItems(items: List<Schedule>): List<Schedule> {
        return items.sortedWith(compareByDescending<Schedule> { it.isPinned }.thenBy { it.startDate })
    }
}
