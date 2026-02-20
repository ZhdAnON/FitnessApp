package com.zhdanon.fitnessapp.presentation.workouts.editor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.ExerciseUi
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.WorkoutSetDraft

@Composable
fun WorkoutSetEditor(
    set: WorkoutSetDraft,
    exercises: List<ExerciseUi>,
    onChange: (WorkoutSetDraft) -> Unit,
    onDeleteSet: () -> Unit,
    onMoveUp: () -> Unit,
    onMoveDown: () -> Unit,
    onMoveExUp: (exerciseIndex: Int) -> Unit,
    onMoveExDown: (exerciseIndex: Int) -> Unit,
    onDeleteExercise: (Int) -> Unit,
) {
    var showExerciseDialog by remember { mutableStateOf(false) }
    var editingExerciseIndex by remember { mutableStateOf<Int?>(null) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(Modifier.padding(12.dp)) {

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Сет", style = MaterialTheme.typography.titleMedium)

                Row {
                    IconButton(onClick = onMoveUp) {
                        Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Вверх")
                    }
                    IconButton(onClick = onMoveDown) {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Вниз")
                    }
                    IconButton(onClick = onDeleteSet) {
                        Icon(Icons.Default.Delete, contentDescription = "Удалить сет")
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            RoundsEditor(
                rounds = set.rounds,
                onChange = { updated ->
                    onChange(set.copy(rounds = updated))
                }
            )

            Spacer(Modifier.height(8.dp))

            val getExerciseName: (String) -> String = { id ->
                exercises.firstOrNull { it.id == id }?.name ?: "Неизвестное упражнение"
            }

            set.exercises.forEachIndexed { index, exercise ->
                WorkoutExerciseItem(
                    exercise = exercise,
                    exerciseName = getExerciseName(exercise.exerciseId),
                    onEdit = {
                        editingExerciseIndex = index
                        showExerciseDialog = true
                    },
                    onMoveUp = { onMoveExUp(index) },
                    onMoveDown = { onMoveExDown(index) },
                    onDelete = { onDeleteExercise(index) }
                )


                Spacer(Modifier.height(12.dp))
            }

            Button(onClick = {
                editingExerciseIndex = null
                showExerciseDialog = true
            }) {
                Text("Добавить упражнение")
            }

            if (showExerciseDialog) {
                val initial = editingExerciseIndex?.let { set.exercises[it] }

                ExerciseDialog(
                    initial = initial,
                    onDismiss = { showExerciseDialog = false },
                    onConfirm = { newExercise ->
                        val updated = set.exercises.toMutableList()

                        if (editingExerciseIndex == null) {
                            updated.add(newExercise)
                        } else {
                            updated[editingExerciseIndex!!] = newExercise
                        }

                        onChange(set.copy(exercises = updated))
                        showExerciseDialog = false
                    }
                )
            }
        }
    }
}