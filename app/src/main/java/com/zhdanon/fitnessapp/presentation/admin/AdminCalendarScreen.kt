package com.zhdanon.fitnessapp.presentation.admin

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import com.zhdanon.fitnessapp.presentation.workouts.workoutsList.components.WorkoutCalendar
import com.zhdanon.fitnessapp.presentation.workouts.workoutsList.components.WorkoutsList
import com.zhdanon.fitnessapp.presentation.workouts.workoutsList.WorkoutViewModel
import org.koin.compose.viewmodel.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdminCalenderScreen(
    navController: NavController,
    workoutViewModel: WorkoutViewModel = koinViewModel(),
    onWorkoutClick: (String) -> Unit = {}
) {
    val state = workoutViewModel.uiState

    val savedStateHandle = LocalContext.current
        .let { LocalViewModelStoreOwner.current }
        ?.let { navController.currentBackStackEntry?.savedStateHandle }

    val workoutSaved = savedStateHandle
        ?.getStateFlow("workout_saved", false)
        ?.collectAsState()

    LaunchedEffect(workoutSaved?.value) {
        if (workoutSaved?.value == true) {
            workoutViewModel.loadWorkouts()   // ← перезагружаем список
            savedStateHandle?.set("workout_saved", false)
        }
    }


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