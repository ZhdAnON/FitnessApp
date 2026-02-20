package com.zhdanon.fitnessapp.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhdanon.fitnessapp.domain.models.auth.UserRole
import com.zhdanon.fitnessapp.domain.usecases.auth.AutoLoginUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.FetchProfileUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.GetSavedTokenUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthStateViewModel(
    private val getSavedTokenUseCase: GetSavedTokenUseCase,
    private val autoLoginUseCase: AutoLoginUseCase,
    private val fetchProfileUseCase: FetchProfileUseCase
) : ViewModel() {

    var startDestination by mutableStateOf<String?>(null)
        private set

    init {
        viewModelScope.launch {
            val savedToken = getSavedTokenUseCase().first()

            if (savedToken == null) {
                startDestination = "login"
                return@launch
            }

            val refreshed = autoLoginUseCase()

            if (!refreshed) {
                startDestination = "login"
                return@launch
            }

            val user = runCatching { fetchProfileUseCase() }.getOrNull()

            startDestination = when (user?.role) {
                UserRole.ADMIN -> "admin"
                UserRole.USER -> "user"
                else -> {
                    "login"
                }
            }
        }
    }
}