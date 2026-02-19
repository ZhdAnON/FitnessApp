package com.zhdanon.fitnessapp.presentation.user

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zhdanon.fitnessapp.presentation.auth.AuthViewModel
import com.zhdanon.fitnessapp.presentation.workouts.workoutsList.components.WorkoutCalendar
import com.zhdanon.fitnessapp.presentation.workouts.workoutsList.components.WorkoutsList
import com.zhdanon.fitnessapp.presentation.workouts.workoutsList.WorkoutViewModel
import org.koin.compose.viewmodel.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserRootScreen(
    viewModel: WorkoutViewModel = koinViewModel(),
    authViewModel: AuthViewModel,
    onWorkoutClick: (String) -> Unit = {}
) {
    val state = viewModel.uiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Тренировки") },
                actions = {
                    TextButton(onClick = { authViewModel.logout() }) {
                        Text("Выйти")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Календарь
            WorkoutCalendar(
                selectedDate = viewModel.selectedDate,
                workoutDates = viewModel.workoutDates,
                onDateSelected = { viewModel.onDateSelected(it) }
            )

            Spacer(Modifier.height(16.dp))

            // Список тренировок выбранной даты
            when {
                state.isLoading -> CircularProgressIndicator()
                state.error != null -> Text("Ошибка: ${state.error}")
                else -> {
                    val workouts = viewModel.workoutsOfSelectedDate

                    if (workouts.isEmpty()) {
                        Text(
                            text = "Выходной",
                            modifier = Modifier.padding(16.dp),
                            color = Color.Gray
                        )
                    } else {
                        WorkoutsList(
                            workouts = workouts,
                            isAdmin = false,
                            onClick = onWorkoutClick,
                            onDelete = { }
                        )
                    }
                }
            }
        }
    }
}