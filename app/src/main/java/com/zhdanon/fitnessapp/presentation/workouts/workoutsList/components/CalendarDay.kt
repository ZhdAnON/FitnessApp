package com.zhdanon.fitnessapp.presentation.workouts.workoutsList.components

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.YearMonth

data class CalendarDay(
    val date: LocalDate,
    val isCurrentMonth: Boolean,
    val isWorkoutDay: Boolean
)

@RequiresApi(Build.VERSION_CODES.O)
fun generateMonthDays(
    month: YearMonth,
    workoutDates: List<LocalDate>
): List<CalendarDay> {
    val firstDay = month.atDay(1)
    val lastDay = month.atEndOfMonth()

    val firstDayOfWeek = (firstDay.dayOfWeek.value + 6) % 7
    val totalDays = lastDay.dayOfMonth

    val days = mutableListOf<CalendarDay>()

    // Пустые ячейки перед началом месяца
    repeat(firstDayOfWeek) {
        days.add(
            CalendarDay(
                date = firstDay.minusDays((firstDayOfWeek - it).toLong()),
                isCurrentMonth = false,
                isWorkoutDay = false
            )
        )
    }

    // Дни текущего месяца
    for (day in 1..totalDays) {
        val date = month.atDay(day)
        days.add(
            CalendarDay(
                date = date,
                isCurrentMonth = true,
                isWorkoutDay = workoutDates.contains(date)
            )
        )
    }

    // Дни после конца месяца (до заполнения сетки 6×7)
    while (days.size % 7 != 0) {
        val last = days.last().date
        days.add(
            CalendarDay(
                date = last.plusDays(1),
                isCurrentMonth = false,
                isWorkoutDay = false
            )
        )
    }

    return days
}