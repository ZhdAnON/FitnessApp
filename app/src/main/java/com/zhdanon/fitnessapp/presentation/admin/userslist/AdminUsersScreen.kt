package com.zhdanon.fitnessapp.presentation.admin.userslist

import ChangePasswordDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zhdanon.fitnessapp.presentation.admin.userslist.components.AddUserDialog
import com.zhdanon.fitnessapp.presentation.admin.userslist.components.ChangeRoleDialog
import com.zhdanon.fitnessapp.presentation.admin.userslist.components.ConfirmDeleteDialog
import com.zhdanon.fitnessapp.presentation.admin.userslist.components.UserItem
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AdminUsersScreen(
    viewModel: AdminUsersViewModel = koinViewModel()
) {
    val state = viewModel.uiState
    var showAddDialog by remember { mutableStateOf(false) }
    var showPasswordDialog by remember { mutableStateOf<String?>(null) }
    var showRoleDialog by remember { mutableStateOf<String?>(null) }

    var userToDelete by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Пользователи",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(16.dp))

        if (state.isLoading) {
            CircularProgressIndicator()
            return@Column
        }

        if (state.error != null) {
            Text(
                text = "Ошибка: ${state.error}",
                color = MaterialTheme.colorScheme.error
            )
            Spacer(Modifier.height(8.dp))
        }

        Button(onClick = { showAddDialog = true }) {
            Text("Добавить пользователя")
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(state.users) { user ->
                UserItem(
                    user = user,
                    onChangeRole = { showRoleDialog = user.id },
                    onChangePassword = { showPasswordDialog = user.id },
                    onDelete = { userToDelete = user.id }
                )
            }
        }
    }

    userToDelete?.let { id ->
        ConfirmDeleteDialog(
            onDismiss = { userToDelete = null },
            onConfirm = {
                viewModel.deleteUser(id)
                userToDelete = null
            }
        )
    }

    if (showAddDialog) {
        AddUserDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { email, password, role ->
                viewModel.addUser(email, password, role)
                showAddDialog = false
            }
        )
    }

    showPasswordDialog?.let { userId ->
        ChangePasswordDialog(
            onDismiss = { showPasswordDialog = null },
            onSave = { newPassword ->
                viewModel.updatePassword(userId, newPassword)
                showPasswordDialog = null
            }
        )
    }

    showRoleDialog?.let { userId ->
        ChangeRoleDialog(
            onDismiss = { showRoleDialog = null },
            onSave = { newRole ->
                viewModel.updateRole(userId, newRole)
                showRoleDialog = null
            }
        )
    }
}