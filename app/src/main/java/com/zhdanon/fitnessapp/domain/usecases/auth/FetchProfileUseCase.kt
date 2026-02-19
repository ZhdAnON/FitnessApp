package com.zhdanon.fitnessapp.domain.usecases.auth

import com.zhdanon.fitnessapp.domain.models.auth.User
import com.zhdanon.fitnessapp.domain.repositories.AuthRepository

class FetchProfileUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): User = authRepository.fetchProfile()
}