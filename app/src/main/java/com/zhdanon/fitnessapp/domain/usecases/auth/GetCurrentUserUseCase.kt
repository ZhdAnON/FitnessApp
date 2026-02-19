package com.zhdanon.fitnessapp.domain.usecases.auth

import com.zhdanon.fitnessapp.domain.models.auth.User
import com.zhdanon.fitnessapp.domain.repositories.AuthRepository

class GetCurrentUserUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): User? = authRepository.getCurrentUser()
}