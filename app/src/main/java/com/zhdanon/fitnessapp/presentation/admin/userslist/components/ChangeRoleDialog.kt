package com.zhdanon.fitnessapp.presentation.admin.userslist.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.zhdanon.fitnessapp.domain.models.auth.UserRole

@Composable
fun ChangeRoleDialog(
    onDismiss: () -> Unit,
    onSave: (UserRole) -> Unit
) {
    var role by remember { mutableStateOf(UserRole.USER) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Изменить роль") },
        text = {
            RoleSelector(role = role, onChange = { role = it })
        },
        confirmButton = {
            TextButton(onClick = { onSave(role) }) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Отмена") }
        }
    )
}