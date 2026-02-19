package com.zhdanon.fitnessapp.domain.usecases.auth

import com.zhdanon.fitnessapp.domain.repositories.UserRepository

class UpdateUserPasswordUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        userId: String,
        newPassword: String
    ) {
        userRepository.updateUserPassword(userId, newPassword)
    }
}