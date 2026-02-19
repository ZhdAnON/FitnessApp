package com.zhdanon.fitnessapp.presentation.admin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhdanon.fitnessapp.domain.models.auth.User
import com.zhdanon.fitnessapp.domain.models.auth.UserRole
import com.zhdanon.fitnessapp.domain.usecases.auth.AddUserUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.DeleteUserUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.GetAllUsersUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.UpdateUserPasswordUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.UpdateUserRoleUseCase
import kotlinx.coroutines.launch

class AdminUsersViewModel(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val updateUserRoleUseCase: UpdateUserRoleUseCase,
    private val updateUserPasswordUseCase: UpdateUserPasswordUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
) : ViewModel() {

    var uiState by mutableStateOf(AdminUsersUiState())
        private set

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                val users = getAllUsersUseCase()
                uiState = uiState.copy(users = users, error = null)
            } catch (e: Exception) {
                uiState = uiState.copy(error = e.message)
            } finally {
                uiState = uiState.copy(isLoading = false)
            }
        }
    }

    fun addUser(email: String, password: String, role: UserRole) {
        viewModelScope.launch {
            try {
                addUserUseCase(email, password, role)
                loadUsers()
            } catch (e: Exception) {
                uiState = uiState.copy(error = e.message)
            }
        }
    }

    fun updateRole(userId: String, role: UserRole) {
        viewModelScope.launch {
            try {
                updateUserRoleUseCase(userId, role)
                loadUsers()
            } catch (e: Exception) {
                uiState = uiState.copy(error = e.message)
            }
        }
    }

    fun updatePassword(userId: String, newPassword: String) {
        viewModelScope.launch {
            try {
                updateUserPasswordUseCase(userId, newPassword)
            } catch (e: Exception) {
                uiState = uiState.copy(error = e.message)
            }
        }
    }

    fun deleteUser(userId: String) {
        viewModelScope.launch {
            try {
                deleteUserUseCase(userId)
                loadUsers()
            } catch (e: Exception) {
                uiState = uiState.copy(error = e.message)
            }
        }
    }
}

data class AdminUsersUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)