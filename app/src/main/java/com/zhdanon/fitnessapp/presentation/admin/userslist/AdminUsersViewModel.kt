package com.zhdanon.fitnessapp.presentation.admin.userslist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhdanon.fitnessapp.domain.models.auth.UserRole
import com.zhdanon.fitnessapp.domain.usecases.auth.AddUserUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.DeleteUserUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.GetAllUsersUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.GetSavedTokenUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.UpdateUserPasswordUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.UpdateUserRoleUseCase
import com.zhdanon.fitnessapp.domain.util.extractUserIdFromJwt
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AdminUsersViewModel(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val updateUserRoleUseCase: UpdateUserRoleUseCase,
    private val updateUserPasswordUseCase: UpdateUserPasswordUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getSavedTokenUseCase: GetSavedTokenUseCase
) : ViewModel() {

    var uiState by mutableStateOf(AdminUsersUiState())
        private set

    private var currentUserId: String? = null

    init {
        viewModelScope.launch {
            val token = getSavedTokenUseCase().first()
            val access = token?.accessToken
            currentUserId = access?.let { extractUserIdFromJwt(it) }
            loadUsers()
        }
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
        if (userId == currentUserId) {
            uiState = uiState.copy(error = "Нельзя удалить самого себя")
            return
        }

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