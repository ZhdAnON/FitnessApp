package com.zhdanon.fitnessapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhdanon.fitnessapp.domain.usecases.auth.LogoutUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _isLoggedOut = MutableSharedFlow<Unit>()
    val isLoggedOut = _isLoggedOut.asSharedFlow()

    fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _isLoggedOut.emit(Unit)
        }
    }
}