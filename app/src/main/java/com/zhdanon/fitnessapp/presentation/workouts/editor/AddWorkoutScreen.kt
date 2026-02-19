package com.zhdanon.fitnessapp.presentation.workouts.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zhdanon.fitnessapp.presentation.workouts.editor.components.WorkoutSetEditor
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.WorkoutUiState
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.WorkoutExerciseDraft
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.WorkoutSetDraft

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkoutScreen(
    state: WorkoutUiState,
    isEditMode: Boolean,
    onTitleChange: (String) -> Unit,
    onDateChange: (LocalDate) -> Unit,
    onAddSet: () -> Unit,
    onUpdateSet: (Int, WorkoutSetDraft) -> Unit,
    onDeleteSet: (Int) -> Unit,
    onMoveSetUp: (Int) -> Unit,
    onMoveSetDown: (Int) -> Unit,
    onMoveExUp: (setIndex: Int, exerciseIndex: Int) -> Unit,
    onMoveExDown: (setIndex: Int, exerciseIndex: Int) -> Unit,
    onDeleteExercise: (setIndex: Int, exerciseIndex: Int) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Заголовок зависит от режима
        Text(
            text = if (isEditMode) "Редактирование тренировки" else "Новая тренировка",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(4.dp))

        OutlinedTextField(
            value = state.title,
            onValueChange = onTitleChange,
            label = { Text("Название") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Button(onClick = { showDatePicker = true }) {
            Text("Указать дату")
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null) {
                            val selectedDate = Instant
                                .ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            onDateChange(selectedDate)
                        }
                        showDatePicker = false
                    }) { Text("ОК") }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) { Text("Отмена") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        Spacer(Modifier.height(8.dp))

        Text("Сеты", style = MaterialTheme.typography.titleMedium)

        // Редактирование + удаление сетов и упражнений
        state.sets.forEachIndexed { index, set ->
            WorkoutSetEditor(
                set = set,
                onChange = { onUpdateSet(index, it) },
                onDeleteSet = { onDeleteSet(index) },
                onDeleteExercise = { exerciseIndex ->
                    onDeleteExercise(index, exerciseIndex)
                },
                onMoveUp = { onMoveSetUp(index) },
                onMoveDown = { onMoveSetDown(index) },
                onMoveExUp = { exerciseIndex -> onMoveExUp(index, exerciseIndex) },
                onMoveExDown = { exerciseIndex -> onMoveExDown(index, exerciseIndex) },
                onAddExercise = {
                    val updated = set.copy(
                        exercises = set.exercises + WorkoutExerciseDraft(exerciseId = "")
                    )
                    onUpdateSet(index, updated)
                }
            )
            Spacer(Modifier.height(12.dp))
        }

        Button(onClick = onAddSet) {
            Text("Добавить сет")
        }

        Spacer(Modifier.height(24.dp))

        // Кнопка сохранения зависит от режима
        Button(
            onClick = onSave,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isEditMode) "Сохранить изменения" else "Создать тренировку")
        }
    }
}