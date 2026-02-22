package com.zhdanon.fitnessapp.domain.usecases.auth

import com.zhdanon.fitnessapp.domain.repositories.UserRepository

class ChangeOwnPasswordUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(newPassword: String) {
        userRepository.changeOwnPassword(newPassword)
    }
}