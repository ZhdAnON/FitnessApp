package com.zhdanon.fitnessapp.presentation.admin

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zhdanon.fitnessapp.presentation.workouts.workoutsList.components.WorkoutCalendar
import com.zhdanon.fitnessapp.presentation.workouts.workoutsList.components.WorkoutsList
import com.zhdanon.fitnessapp.presentation.workouts.workoutsList.WorkoutViewModel
import org.koin.compose.viewmodel.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdminCalenderScreen(
    workoutViewModel: WorkoutViewModel = koinViewModel(),
    onWorkoutClick: (String) -> Unit = {}
) {
    val state = workoutViewModel.uiState

    Column {
        // Календарь
        WorkoutCalendar(
            selectedDate = workoutViewModel.selectedDate,
            workoutDates = workoutViewModel.workoutDates,
            onDateSelected = { workoutViewModel.onDateSelected(it) }
        )
        // Список тренировок выбранной даты
        when {
            state.isLoading -> CircularProgressIndicator()
            state.error != null -> Text("Ошибка: ${state.error}")
            else -> {
                val workouts = workoutViewModel.workoutsOfSelectedDate

                if (workouts.isEmpty()) {
                    Text(
                        text = "Выходной",
                        modifier = Modifier.padding(16.dp),
                        color = Color.Gray
                    )
                } else {
                    WorkoutsList(
                        workouts = workouts,
                        isAdmin = true,
                        onClick = onWorkoutClick,
                        onDelete = workoutViewModel::deleteWorkout
                    )
                }
            }
        }
    }
}