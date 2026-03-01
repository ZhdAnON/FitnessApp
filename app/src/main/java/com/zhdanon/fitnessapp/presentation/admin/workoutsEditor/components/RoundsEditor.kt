package com.zhdanon.fitnessapp.presentation.admin.workoutsEditor.components

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
import com.zhdanon.fitnessapp.presentation.admin.workoutsEditor.draft.RoundsDraft
import com.zhdanon.fitnessapp.presentation.admin.workoutsEditor.draft.RoundsTypeUi

@Composable
fun RoundsEditor(
    rounds: RoundsDraft,
    onChange: (RoundsDraft) -> Unit
) {
    val type = rounds.type
    var error by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

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
                            RoundsTypeUi.FIXED -> RoundsDraft.Fixed("")
                            RoundsTypeUi.RANGE -> RoundsDraft.Range("", "")
                            RoundsTypeUi.TIME_FIXED -> RoundsDraft.TimeFixed("")
                            RoundsTypeUi.TIME_RANGE -> RoundsDraft.TimeRange("", "")
                        }
                        error = false
                        onChange(newValue)
                    }
                )
            }

            // Значения
            when (rounds) {

                is RoundsDraft.Fixed -> {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = rounds.count,
                            onValueChange = { v ->
                                onChange(rounds.copy(count = v))
                            },
                            label = { Text("Количество кругов") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                is RoundsDraft.Range -> {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = rounds.from,
                            onValueChange = { v ->
                                val updated = rounds.copy(from = v)
                                error = updated.from.toIntOrNull()?.let { f ->
                                    updated.to.toIntOrNull()?.let { t -> f > t }
                                } ?: false
                                onChange(updated)
                            },
                            label = { Text("От") },
                            isError = error,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = rounds.to,
                            onValueChange = { v ->
                                val updated = rounds.copy(to = v)
                                error = updated.from.toIntOrNull()?.let { f ->
                                    updated.to.toIntOrNull()?.let { t -> f > t }
                                } ?: false
                                onChange(updated)
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
                            value = rounds.duration,
                            onValueChange = { v ->
                                onChange(rounds.copy(duration = v))
                            },
                            label = { Text("Время (мин)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                is RoundsDraft.TimeRange -> {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = rounds.from,
                            onValueChange = { v ->
                                val updated = rounds.copy(from = v)
                                error = updated.from.toIntOrNull()?.let { f ->
                                    updated.to.toIntOrNull()?.let { t -> f > t }
                                } ?: false
                                onChange(updated)
                            },
                            label = { Text("От (мин)") },
                            isError = error,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = rounds.to,
                            onValueChange = { v ->
                                val updated = rounds.copy(to = v)
                                error = updated.from.toIntOrNull()?.let { f ->
                                    updated.to.toIntOrNull()?.let { t -> f > t }
                                } ?: false
                                onChange(updated)
                            },
                            label = { Text("До (мин)") },
                            isError = error,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

        if (error) {
            Text(
                text = "Значение 'От' должно быть ≤ 'До'",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}