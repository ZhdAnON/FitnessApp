package com.zhdanon.fitnessapp.presentation.workouts.editor.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.zhdanon.fitnessapp.presentation.workouts.exercises.ExerciseListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExerciseDropdown(
    selectedId: String?,
    onSelected: (String) -> Unit,
    viewModel: ExerciseListViewModel = koinViewModel()
) {
    val exercises = viewModel.exercises
    var expanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            value = exercises.firstOrNull { it.id == selectedId }?.name ?: "",
            onValueChange = {},
            label = { Text("Упражнение") },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, null)
                }
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            exercises.forEach { ex ->
                DropdownMenuItem(
                    text = { Text(ex.name) },
                    onClick = {
                        expanded = false
                        onSelected(ex.id)
                    }
                )
            }
        }
    }
}