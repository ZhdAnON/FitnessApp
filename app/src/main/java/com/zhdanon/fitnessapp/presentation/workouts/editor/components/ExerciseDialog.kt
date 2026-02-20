package com.zhdanon.fitnessapp.presentation.workouts.editor.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.RepsDraft
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.WorkoutExerciseDraft

@Composable
fun ExerciseDialog(
    initial: WorkoutExerciseDraft?,
    onDismiss: () -> Unit,
    onConfirm: (WorkoutExerciseDraft) -> Unit
) {
    var exerciseId by remember { mutableStateOf(initial?.exerciseId ?: "") }
    var reps by remember { mutableStateOf(initial?.reps ?: RepsDraft.Fixed(count = 1)) }
    var note by remember { mutableStateOf(initial?.note ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initial == null) "Добавить упражнение" else "Редактировать упражнение") },
        text = {
            Column {

                ExerciseDropdown(
                    selectedId = exerciseId,
                    onSelected = { exerciseId = it }
                )

                Spacer(Modifier.height(8.dp))

                RepsEditor(
                    reps = reps,
                    onChange = { reps = it }
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text("Примечание") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(
                    WorkoutExerciseDraft(
                        exerciseId = exerciseId,
                        reps = reps,
                        note = note
                    )
                )
            }) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}