package com.zhdanon.fitnessapp.domain.usecases.auth

import com.zhdanon.fitnessapp.domain.models.auth.UserRole
import com.zhdanon.fitnessapp.domain.repositories.UserRepository

class UpdateUserRoleUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        userId: String,
        role: UserRole
    ) {
        userRepository.updateUserRole(userId, role)
    }
}