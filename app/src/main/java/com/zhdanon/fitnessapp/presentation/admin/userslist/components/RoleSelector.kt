package com.zhdanon.fitnessapp.presentation.admin.userslist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zhdanon.fitnessapp.domain.models.auth.UserRole

@Composable
fun RoleSelector(role: UserRole, onChange: (UserRole) -> Unit) {
    Column {
        UserRole.values().forEach { r ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onChange(r) }
                    .padding(4.dp)
            ) {
                RadioButton(
                    selected = r == role,
                    onClick = { onChange(r) }
                )
                Text(r.name)
            }
        }
    }
}