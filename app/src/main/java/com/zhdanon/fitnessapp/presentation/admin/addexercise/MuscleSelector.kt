package com.zhdanon.fitnessapp.presentation.admin.addexercise

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import com.zhdanon.fitnessapp.domain.models.workouts.Muscle
import com.zhdanon.fitnessapp.domain.models.workouts.MuscleCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MuscleSelector(
    selected: List<Muscle>,
    onSelectedChange: (List<Muscle>) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selected.joinToString { it.title },
            onValueChange = {},
            readOnly = true,
            label = { Text("Мышечные группы") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            MuscleCategory.all.forEach { category ->

                // Заголовок категории
                DropdownMenuItem(
                    text = {
                        Text(
                            text = category.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    onClick = {},
                    enabled = false
                )

                // Подкатегории
                category.muscles.forEach { muscle ->
                    val isSelected = muscle in selected

                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = isSelected,
                                    onCheckedChange = null
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(muscle.title)
                            }
                        },
                        onClick = {
                            val newList =
                                if (isSelected) selected - muscle
                                else selected + muscle

                            onSelectedChange(newList)
                        }
                    )
                }

                Divider()
            }
        }
    }
}