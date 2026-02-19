package com.zhdanon.fitnessapp.presentation.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhdanon.fitnessapp.domain.models.auth.UserRole
import com.zhdanon.fitnessapp.domain.usecases.auth.FetchProfileUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.GetCurrentUserUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.GetSavedTokenUseCase
import kotlinx.coroutines.launch

class AuthStateViewModel(
    private val getSavedTokenUseCase: GetSavedTokenUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val fetchProfileUseCase: FetchProfileUseCase
) : ViewModel() {

    var startDestination by mutableStateOf<String?>(null)
        private set

    var sessionExpired by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch {
            getSavedTokenUseCase().collect { token ->

                if (token == null) {
                    // токен исчез → либо logout, либо refresh истёк
                    sessionExpired = true
                    startDestination = "login"
                    return@collect
                }

                // токен есть → проверяем пользователя
                val user = getCurrentUserUseCase() ?: runCatching {
                    fetchProfileUseCase()
                }.getOrNull()

                if (user == null) {
                    // если профиль не загрузился → считаем, что сессия истекла
                    sessionExpired = true
                    startDestination = "login"
                } else {
                    sessionExpired = false
                    startDestination = when (user.role) {
                        UserRole.ADMIN -> "admin"
                        UserRole.USER -> "user"
                    }
                }
            }
        }
    }
}