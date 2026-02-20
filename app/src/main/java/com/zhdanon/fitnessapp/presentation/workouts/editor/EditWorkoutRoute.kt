package com.zhdanon.fitnessapp.presentation.workouts.editor

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.zhdanon.fitnessapp.domain.models.workouts.Workout

@Composable
fun EditWorkoutRoute(
    workoutId: String,
    viewModel: EditWorkoutViewModel,
    onSaved: () -> Unit
) {
    val workout = remember { mutableStateOf<Workout?>(null) }
    val snackHostState = remember { SnackbarHostState() }

    LaunchedEffect(workoutId) {
        val loaded = viewModel.getWorkout(workoutId)
        workout.value = loaded
        if (loaded != null) {
            viewModel.loadWorkoutForEdit(loaded)
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            if (event is EditWorkoutViewModel.EditWorkoutEvent.Saved) {
                snackHostState.showSnackbar("Изменения сохранены")
                onSaved()
            }
        }
    }

    val state = viewModel.uiState

    // ⬇⬇⬇ ВАЖНО: ждём загрузки упражнений
    if (workout.value == null || state.exercises.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackHostState) }
    ) { padding ->
        AddWorkoutScreen(
            state = state,
            isEditMode = true,
            onTitleChange = viewModel::setTitle,
            onDateChange = viewModel::setDate,
            onAddSet = viewModel::addSet,
            onUpdateSet = viewModel::updateSet,
            onDeleteSet = viewModel::deleteSet,
            onMoveSetUp = viewModel::moveSetUp,
            onMoveSetDown = viewModel::moveSetDown,
            onMoveExUp = viewModel::moveExerciseUp,
            onMoveExDown = viewModel::moveExerciseDown,
            onDeleteExercise = viewModel::deleteExercise,
            onSave = viewModel::updateWorkout,
            modifier = Modifier.padding(padding)
        )
    }
}