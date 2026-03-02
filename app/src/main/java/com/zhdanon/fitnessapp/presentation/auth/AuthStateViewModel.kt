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

    private var initialized = false

    init {
        initialize()
    }

    private fun initialize() {
        if (initialized) return
        initialized = true

        viewModelScope.launch {
            // 1. Ждём первый эмит токена из DataStore
            val savedToken = getSavedTokenUseCase().first()

            // 2. Если токена нет — сразу на экран логина
            if (savedToken == null) {
                startDestination = "login"
                return@launch
            }

            // 3. Пробуем обновить токен (refresh)
            val refreshed = autoLoginUseCase()
            if (!refreshed) {
                startDestination = "login"
                return@launch
            }

            // 4. Пробуем получить профиль
            val user = runCatching { fetchProfileUseCase() }.getOrNull()

            startDestination = when (user?.role) {
                UserRole.ADMIN -> "admin"
                UserRole.USER -> "user"
                else -> "login"
            }
        }
    }
}