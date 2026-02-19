package com.zhdanon.fitnessapp.presentation.workouts.exercises

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.foundation.lazy.items


@Composable
fun ExerciseListScreen(
    onAddExercise: () -> Unit,
    viewModel: ExerciseListViewModel = koinViewModel()
) {
    val exercises = viewModel.exercises
    val isLoading = viewModel.isLoading
    val error = viewModel.error

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddExercise) {
                Icon(Icons.Default.Add, contentDescription = "Add exercise")
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {

            when {
                isLoading -> CircularProgressIndicator()
                error != null -> Text("Ошибка: $error")
                exercises.isEmpty() -> Text("Упражнений пока нет")
                else -> LazyColumn {
                    items(exercises) { ex ->
                        Column(Modifier.padding(16.dp)) {
                            Text(ex.name, style = MaterialTheme.typography.titleMedium)
                            Text(ex.muscleGroups.joinToString(", "))
                            Spacer(Modifier.height(8.dp))
                        }
                    }
                }
            }
        }
    }
}