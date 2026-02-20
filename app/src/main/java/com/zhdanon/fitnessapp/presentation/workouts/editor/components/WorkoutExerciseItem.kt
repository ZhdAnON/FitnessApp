package com.zhdanon.fitnessapp.presentation.workouts.editor.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.RepsDraft
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.WorkoutExerciseDraft

@Composable
fun WorkoutExerciseItem(
    exercise: WorkoutExerciseDraft,
    exerciseName: String,
    onEdit: () -> Unit,
    onMoveUp: () -> Unit,
    onMoveDown: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onEdit() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {

            // Левая часть — текст
            Column(Modifier.weight(1f)) {

                Text(
                    text = exerciseName,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = repsToText(exercise.reps),
                    style = MaterialTheme.typography.bodyMedium
                )

                if (exercise.note.isNotBlank()) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = exercise.note,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Правая часть — блок с кнопками
            Row(
                verticalAlignment = Alignment.CenterVertically // 🗑 центрируется относительно колонки ↑/↓
            ) {

                // Колонка ↑ / ↓
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    IconButton(
                        onClick = onMoveUp,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            Icons.Default.KeyboardArrowUp,
                            contentDescription = "Вверх",
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(Modifier.height(2.dp))

                    IconButton(
                        onClick = onMoveDown,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            Icons.Default.KeyboardArrowDown,
                            contentDescription = "Вниз",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(Modifier.width(4.dp))

                // Кнопка удаления — справа, по центру относительно ↑/↓
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Удалить",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

fun repsToText(reps: RepsDraft): String = when (reps) {
    is RepsDraft.Fixed -> "Повторы: ${reps.count}"
    is RepsDraft.Range -> "Повторы: ${reps.from}–${reps.to}"
    is RepsDraft.TimeFixed -> "Время: ${reps.duration} сек"
    is RepsDraft.TimeRange -> "Время: ${reps.from}–${reps.to} сек"
    RepsDraft.None -> "Максимум"
}