package com.zhdanon.fitnessapp.presentation.workouts.workoutDetail

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WorkoutDetailRoute(
    workoutId: String,
    isAdmin: Boolean,
    workoutViewModel: WorkoutDetailViewModel = koinViewModel(),
    onEdit: (String) -> Unit,
    onExerciseClick: (String) -> Unit
) {
    val state = workoutViewModel.uiState

    LaunchedEffect(workoutId) {
        workoutViewModel.loadWorkout(workoutId)
    }

    when {
        state.isLoading -> CircularProgressIndicator()
        state.error != null -> Text("Ошибка: ${state.error}")
        state.workout != null ->
            WorkoutDetailScreen(
                workout = state.workout,
                exercises = state.exercises,
                onEdit = onEdit,
                isAdmin = isAdmin,
                onExerciseClick = onExerciseClick
            )
    }
}