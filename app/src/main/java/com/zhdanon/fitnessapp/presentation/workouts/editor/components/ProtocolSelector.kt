package com.zhdanon.fitnessapp.presentation.workouts.editor.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.zhdanon.fitnessapp.domain.models.workouts.ProtocolType

@Composable
fun ProtocolSelector(
    value: ProtocolType,
    onChange: (ProtocolType) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier) {

        // Само поле (не кликабельное)
        OutlinedTextField(
            value = value.name,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text("Протокол") },
            readOnly = true,
            enabled = false, // ← важно: поле НЕ активно
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
            }
        )

        // Прозрачная кликабельная зона поверх поля
        Box(
            Modifier
                .matchParentSize()
                .clickable { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            ProtocolType.values().forEach { protocol ->
                DropdownMenuItem(
                    text = { Text(protocol.name) },
                    onClick = {
                        expanded = false
                        onChange(protocol)
                    }
                )
            }
        }
    }
}