package com.zhdanon.fitnessapp.presentation.admin.addexercise

import ExerciseEditorScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddExerciseRoute(
    navController: NavHostController,
    viewModel: AddExerciseViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            if (event is AddExerciseViewModel.AddExerciseEvent.Saved) {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("exercise_created", true)
                navController.popBackStack()
            }
        }
    }

    ExerciseEditorScreen(
        isEditMode = false,
        onSaved = { navController.popBackStack() },
        name = viewModel.name,
        onNameChange = { viewModel.name = it },
        selectedMuscles = viewModel.selectedMuscles,
        onMusclesChange = { viewModel.selectedMuscles = it },
        technique = viewModel.technique,
        onTechniqueChange = { viewModel.technique = it },
        videoUrl = viewModel.videoUrl,
        onVideoUrlChange = { viewModel.videoUrl = it },
        onSaveClick = viewModel::save,
        error = viewModel.error
    )
}