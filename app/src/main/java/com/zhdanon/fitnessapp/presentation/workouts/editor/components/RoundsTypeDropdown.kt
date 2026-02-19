package com.zhdanon.fitnessapp.presentation.workouts.editor.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.RoundsTypeUi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundsTypeDropdown(
    selected: RoundsTypeUi,
    onSelected: (RoundsTypeUi) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val label = when (selected) {
        RoundsTypeUi.FIXED -> "Кол-во"
        RoundsTypeUi.RANGE -> "Кол-во"
        RoundsTypeUi.TIME_FIXED -> "Время"
        RoundsTypeUi.TIME_RANGE -> "Время"
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = label,
            onValueChange = {},
            readOnly = true,
            label = { Text("Круги") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Кол-во") },
                onClick = {
                    onSelected(RoundsTypeUi.FIXED)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Кол-во (от/до)") },
                onClick = {
                    onSelected(RoundsTypeUi.RANGE)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Время") },
                onClick = {
                    onSelected(RoundsTypeUi.TIME_FIXED)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Время (от/до)") },
                onClick = {
                    onSelected(RoundsTypeUi.TIME_RANGE)
                    expanded = false
                }
            )
        }
    }
}