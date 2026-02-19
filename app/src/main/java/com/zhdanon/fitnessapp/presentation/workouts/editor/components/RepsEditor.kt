package com.zhdanon.fitnessapp.presentation.workouts.editor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.RepsDraft
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.RepsTypeUi

@Composable
fun RepsEditor(
    reps: RepsDraft,
    onChange: (RepsDraft) -> Unit
) {
    val type = reps.type
    var error by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        // --- ОДНА СТРОКА: тип + значения ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Тип выполнения
            Box(modifier = Modifier.weight(1f)) {
                RepsTypeDropdown(
                    selected = type,
                    onSelected = { newType ->
                        val newValue = when (newType) {
                            RepsTypeUi.FIXED -> RepsDraft.Fixed(1)
                            RepsTypeUi.RANGE -> RepsDraft.Range(1, 2)
                            RepsTypeUi.TIME_FIXED -> RepsDraft.TimeFixed(60)
                            RepsTypeUi.TIME_RANGE -> RepsDraft.TimeRange(60, 120)
                            RepsTypeUi.MAX -> RepsDraft.None
                        }
                        error = false
                        onChange(newValue)
                    }
                )
            }

            // Значения (в зависимости от типа)
            when (reps) {

                is RepsDraft.Fixed -> {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = reps.count.toString(),
                            onValueChange = { v ->
                                v.toIntOrNull()?.let {
                                    onChange(reps.copy(count = it))
                                }
                            },
                            label = { Text("Кол-во") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                is RepsDraft.Range -> {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = reps.from.toString(),
                            onValueChange = { v ->
                                v.toIntOrNull()?.let {
                                    val updated = reps.copy(from = it)
                                    error = updated.from > updated.to
                                    onChange(updated)
                                }
                            },
                            label = { Text("От") },
                            isError = error,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = reps.to.toString(),
                            onValueChange = { v ->
                                v.toIntOrNull()?.let {
                                    val updated = reps.copy(to = it)
                                    error = updated.from > updated.to
                                    onChange(updated)
                                }
                            },
                            label = { Text("До") },
                            isError = error,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                is RepsDraft.TimeFixed -> {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = reps.duration.toString(),
                            onValueChange = { v ->
                                v.toIntOrNull()?.let {
                                    onChange(reps.copy(duration = it))
                                }
                            },
                            label = { Text("Сек") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                is RepsDraft.TimeRange -> {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = reps.from.toString(),
                            onValueChange = { v ->
                                v.toIntOrNull()?.let {
                                    val updated = reps.copy(from = it)
                                    error = updated.from > updated.to
                                    onChange(updated)
                                }
                            },
                            label = { Text("От (сек)") },
                            isError = error,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = reps.to.toString(),
                            onValueChange = { v ->
                                v.toIntOrNull()?.let {
                                    val updated = reps.copy(to = it)
                                    error = updated.from > updated.to
                                    onChange(updated)
                                }
                            },
                            label = { Text("До (сек)") },
                            isError = error,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                RepsDraft.None -> {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = "max",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Кол-во") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        // --- Ошибка ---
        if (error) {
            Text(
                text = "Значение 'От' должно быть ≤ 'До'",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}