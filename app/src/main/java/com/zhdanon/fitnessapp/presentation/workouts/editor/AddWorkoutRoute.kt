package com.zhdanon.fitnessapp.presentation.workouts.editor

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddWorkoutRoute(
    isEditMode: Boolean = false,
    viewModel: AddWorkoutViewModel = koinViewModel(),
    onSaved: () -> Unit
) {
    val state = viewModel.uiState
    val snackHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.validationError) {
        state.validationError?.let { msg ->
            snackHostState.showSnackbar(msg)
            viewModel.onValidationErrorShown()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            if (event is AddWorkoutViewModel.AddWorkoutEvent.Saved) {
                snackHostState.showSnackbar("Тренировка сохранена")
                onSaved()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackHostState) }
    ) { padding ->
        AddWorkoutScreen(
            state = state,
            isEditMode = isEditMode,
            onTitleChange = viewModel::setTitle,
            onDateChange = viewModel::setDate,
            onAddSet = viewModel::addSet,
            onUpdateSet = viewModel::updateSet,
            onMoveSetUp = viewModel::moveSetUp,
            onMoveSetDown = viewModel::moveSetDown,
            onMoveExUp = viewModel::moveExerciseUp,
            onMoveExDown = viewModel::moveExerciseDown,
            onDeleteSet = viewModel::deleteSet,
            onDeleteExercise = viewModel::deleteExercise,
            onSave = viewModel::saveWorkout,
            modifier = Modifier.padding(padding)
        )
    }
}