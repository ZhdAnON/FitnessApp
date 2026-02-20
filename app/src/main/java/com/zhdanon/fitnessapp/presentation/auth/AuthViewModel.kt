package com.zhdanon.fitnessapp.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhdanon.fitnessapp.domain.usecases.auth.AutoLoginUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.LogoutUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val autoLoginUseCase: AutoLoginUseCase
) : ViewModel() {

    var isLoggedIn by mutableStateOf<Boolean?>(null)
        private set

    private val _isLoggedOut = MutableSharedFlow<Unit>()
    val isLoggedOut = _isLoggedOut.asSharedFlow()

    init {
        viewModelScope.launch {
            isLoggedIn = autoLoginUseCase()
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _isLoggedOut.emit(Unit)
        }
    }
}