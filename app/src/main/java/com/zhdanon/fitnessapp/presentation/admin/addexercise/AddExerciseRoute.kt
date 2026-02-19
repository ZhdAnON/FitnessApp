package com.zhdanon.fitnessapp.presentation.admin.addexercise

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddExerciseRoute(
    navController: NavHostController,
    viewModel: AddExerciseViewModel = koinViewModel()
) {
    AddExerciseScreen(
        onSaved = { navController.popBackStack() },
        viewModel = viewModel
    )
}