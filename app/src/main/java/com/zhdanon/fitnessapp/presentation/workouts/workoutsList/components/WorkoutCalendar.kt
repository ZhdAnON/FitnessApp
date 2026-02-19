package com.zhdanon.fitnessapp.presentation.workouts.workoutsList.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WorkoutCalendar(
    selectedDate: LocalDate?,
    workoutDates: List<LocalDate>,
    onDateSelected: (LocalDate) -> Unit
) {
    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    val days = generateMonthDays(currentMonth, workoutDates)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        // Заголовок месяца
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "<",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { currentMonth = currentMonth.minusMonths(1) }
            )
            Text(
                "${currentMonth.month} ${currentMonth.year}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                ">",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { currentMonth = currentMonth.plusMonths(1) }
            )
        }

        Spacer(Modifier.height(8.dp))

        // Сетка дней
        Column {
            days.chunked(7).forEach { week ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    week.forEach { day ->
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { onDateSelected(day.date) },
                            contentAlignment = Alignment.Center
                        ) {
                            val bg = when {
                                day.isWorkoutDay -> Color(0xFF4CAF50)
                                !day.isCurrentMonth -> Color.LightGray.copy(alpha = 0.3f)
                                else -> Color.Transparent
                            }

                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(bg, shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = day.date.dayOfMonth.toString(),
                                    color = if (day.isCurrentMonth) Color.Black else Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}