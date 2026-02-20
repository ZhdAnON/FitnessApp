package com.zhdanon.fitnessapp.domain.usecases.auth

import com.zhdanon.fitnessapp.domain.repositories.AuthRepository

class AutoLoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        val token = authRepository.getCurrentToken() ?: return false
        val result = authRepository.refreshToken()
        return result
    }
}