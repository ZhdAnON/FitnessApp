package com.zhdanon.fitnessapp.presentation.workouts.workoutsList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zhdanon.fitnessapp.domain.models.workouts.Workout

@Composable
fun WorkoutItem(
    workout: Workout,
    isAdmin: Boolean,
    onClick: (String) -> Unit,
    onDelete: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Удалить тренировку?") },
            text = { Text("Это действие нельзя отменить.") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    onDelete(workout.id)
                }) {
                    Text("Удалить")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(workout.id) }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = workout.title,
                    modifier = Modifier.alignByBaseline(),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = workout.date.toString(),
                    modifier = Modifier.alignByBaseline(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (isAdmin) {
                Text(
                    text = "Удалить",
                    color = Color.Red,
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .clickable { showDialog = true }
                )
            }
        }
    }
}