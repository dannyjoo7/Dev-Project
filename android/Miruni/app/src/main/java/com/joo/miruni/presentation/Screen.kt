package com.joo.miruni.presentation

import androidx.annotation.StringRes

enum class Screen(val route: String) {
    Home("home"),
    Overdue("overdue"),
    Calendar("calendar"),
    AddTodo("addTodo?selectDate={selectDate}"),
    AddSchedule("addSchedule?selectDate={selectDate}"),
    DetailTodo("detailTodo/{todoId}"),
    DetailSchedule("detailSchedule/{scheduleId}"),
}

data class BottomNavItem(
    @StringRes val labelResId: Int,
    val iconResId: Int,
    val screen: Screen,
)

