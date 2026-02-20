package com.zhdanon.fitnessapp.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhdanon.fitnessapp.domain.models.auth.User
import com.zhdanon.fitnessapp.domain.usecases.auth.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onEmailChange(value: String) {
        uiState = uiState.copy(email = value)
    }

    fun onPasswordChange(value: String) {
        uiState = uiState.copy(password = value)
    }

    fun onLoginClick(onSuccess: (User) -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            try {
                val user = loginUseCase(uiState.email, uiState.password)
                onSuccess(user)
            } catch (e: Exception) {
                uiState = uiState.copy(error = e.message ?: "Ошибка авторизации")
            } finally {
                uiState = uiState.copy(isLoading = false)
            }
        }
    }
}