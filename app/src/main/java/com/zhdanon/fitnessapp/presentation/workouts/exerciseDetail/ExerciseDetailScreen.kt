package com.zhdanon.fitnessapp.presentation.workouts.exerciseDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun ExerciseDetailScreen(
    viewModel: ExerciseDetailViewModel,
    isAdmin: Boolean,
    onEdit: () -> Unit,
    onDeleted: () -> Unit
) {
    val exercise = viewModel.exercise
    val isLoading = viewModel.isLoading
    val error = viewModel.error

    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            if (event is ExerciseDetailViewModel.Event.Deleted) {
                onDeleted()
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Удалить упражнение?") },
            text = { Text("Это действие нельзя отменить.") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    viewModel.delete()
                }) {
                    Text("Удалить")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            isLoading -> CircularProgressIndicator()
            error != null -> Text("Ошибка: $error")
            exercise != null -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    // Название упражнения
                    Text(
                        text = exercise.name,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        softWrap = true,
                        overflow = TextOverflow.Clip
                    )

                    if (isAdmin) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            IconButton(
                                onClick = onEdit,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Редактировать"
                                )
                            }

                            IconButton(
                                onClick = { showDeleteDialog = true },
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = Color.Red
                                ),
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Удалить"
                                )
                            }
                        }
                    }
                }

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