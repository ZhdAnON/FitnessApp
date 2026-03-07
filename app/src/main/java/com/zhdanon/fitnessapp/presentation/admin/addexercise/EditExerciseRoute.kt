package com.zhdanon.fitnessapp.presentation.admin.addexercise

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.zhdanon.fitnessapp.domain.util.uriToFile
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EditExerciseRoute(
    navController: NavHostController,
    viewModel: EditExerciseViewModel = koinViewModel()
) {
    val context = LocalContext.current

    val pickVideoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val file = uriToFile(context, it)
            viewModel.onVideoSelected(uri = it, file = file, name = file.name)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            if (event is EditExerciseViewModel.EditExerciseEvent.Saved) {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("exercise_updated", true)
                navController.popBackStack()
            }
        }
    }

    ExerciseEditorScreen(
        isEditMode = true,
        name = viewModel.name,
        onNameChange = { viewModel.name = it },
        selectedMuscles = viewModel.selectedMuscles,
        onMusclesChange = { viewModel.selectedMuscles = it },
        technique = viewModel.technique,
        onTechniqueChange = { viewModel.technique = it },
        videoUrl = viewModel.videoUrl,
        selectedVideoName = viewModel.selectedVideoName,
        selectedVideoUri = viewModel.selectedVideoUri,
        onPickVideo = { pickVideoLauncher.launch("video/*") },
        onSaveClick = viewModel::save,
        error = viewModel.error
    )
}