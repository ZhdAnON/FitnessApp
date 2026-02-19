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
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.RoundsDraft
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.RoundsTypeUi

@Composable
fun RoundsEditor(
    rounds: RoundsDraft,
    onChange: (RoundsDraft) -> Unit
) {
    val type = rounds.type
    var error by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

        // --- ОДНА СТРОКА: тип + значения ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Выбор типа
            Box(modifier = Modifier.weight(1f)) {
                RoundsTypeDropdown(
                    selected = type,
                    onSelected = { newType ->
                        val newValue = when (newType) {
                            RoundsTypeUi.FIXED -> RoundsDraft.Fixed(1)
                            RoundsTypeUi.RANGE -> RoundsDraft.Range(1, 2)
                            RoundsTypeUi.TIME_FIXED -> RoundsDraft.TimeFixed(60)
                            RoundsTypeUi.TIME_RANGE -> RoundsDraft.TimeRange(60, 120)
                        }
                        onChange(newValue)
                    }
                )
            }

            // Значения (в зависимости от типа)
            when (rounds) {
                is RoundsDraft.Fixed -> {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = rounds.count.toString(),
                            onValueChange = { v ->
                                v.toIntOrNull()?.let { onChange(rounds.copy(count = it)) }
                            },
                            label = { Text("Количество кругов") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                is RoundsDraft.Range -> {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = rounds.from.toString(),
                            onValueChange = { v ->
                                v.toIntOrNull()?.let {
                                    val updated = rounds.copy(from = it)
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
                            value = rounds.to.toString(),
                            onValueChange = { v ->
                                v.toIntOrNull()?.let {
                                    val updated = rounds.copy(to = it)
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

                is RoundsDraft.TimeFixed -> {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = rounds.duration.toString(),
                            onValueChange = { v ->
                                v.toIntOrNull()?.let { onChange(rounds.copy(duration = it)) }
                            },
                            label = { Text("Время (мин)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                is RoundsDraft.TimeRange -> {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = rounds.from.toString(),
                            onValueChange = { v ->
                                v.toIntOrNull()?.let {
                                    val updated = rounds.copy(from = it)
                                    error = updated.from > updated.to
                                    onChange(updated)
                                }
                            },
                            label = { Text("От (мин)") },
                            isError = error,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = rounds.to.toString(),
                            onValueChange = { v ->
                                v.toIntOrNull()?.let {
                                    val updated = rounds.copy(to = it)
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