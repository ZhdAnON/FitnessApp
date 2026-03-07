package com.zhdanon.fitnessapp.presentation.admin.exercises

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.zhdanon.fitnessapp.R
import com.zhdanon.fitnessapp.presentation.background.BackgroundContainer

@Composable
fun ExerciseListScreen(
    navController: NavController,
    onAddExercise: () -> Unit,
    viewModel: ExerciseListViewModel
) {
    val exercises = viewModel.exercises
    val isLoading = viewModel.isLoading
    val error = viewModel.error

    BackgroundContainer(backgroundRes = R.drawable.bg_workouts) {
        Scaffold(
            containerColor = Color.Transparent,
            floatingActionButton = {
                FloatingActionButton(onClick = onAddExercise) {
                    Icon(Icons.Default.Add, contentDescription = "Add exercise")
                }
            }
        ) { padding ->
            when {
                isLoading -> CircularProgressIndicator()
                error != null -> Text("Ошибка: $error")
                exercises.isEmpty() -> Text("Упражнений пока нет")

                else -> Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.8f)
                    )
                ) {
                    LazyColumn(Modifier.padding(8.dp)) {
                        items(exercises) { ex ->
                            Column(
                                Modifier
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                    .clickable { navController.navigate("exercise/${ex.id}") }
                            ) {
                                Text(ex.name, style = MaterialTheme.typography.titleMedium)
                                Text(ex.muscleGroups.joinToString(", "))
                            }
                        }
                    }
                }
            }
        }
    }
}