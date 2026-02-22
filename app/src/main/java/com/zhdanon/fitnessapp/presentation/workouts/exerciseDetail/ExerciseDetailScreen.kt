package com.zhdanon.fitnessapp.presentation.workouts.exerciseDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExerciseDetailScreen(
    viewModel: ExerciseDetailViewModel = koinViewModel()
) {
    val exercise = viewModel.exercise
    val isLoading = viewModel.isLoading
    val error = viewModel.error

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            isLoading -> CircularProgressIndicator()

            error != null -> Text("Ошибка: $error")

            exercise != null -> {
                Text(
                    exercise.name,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(Modifier.height(16.dp))

                Text("Мышечные группы:", style = MaterialTheme.typography.titleMedium)
                Text(exercise.muscleGroups.joinToString(", "))

                Spacer(Modifier.height(16.dp))

                Text("Техника выполнения:", style = MaterialTheme.typography.titleMedium)
                Text(exercise.technique)

                exercise.videoUrl?.let {
                    Spacer(Modifier.height(16.dp))
                    Text("Видео:", style = MaterialTheme.typography.titleMedium)
                    Text(it, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}