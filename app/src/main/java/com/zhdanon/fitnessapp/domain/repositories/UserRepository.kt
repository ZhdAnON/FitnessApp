package com.zhdanon.fitnessapp.domain.repositories

import com.zhdanon.fitnessapp.domain.models.auth.User
import com.zhdanon.fitnessapp.domain.models.auth.UserRole

interface UserRepository {
    suspend fun getAllUsers(): List<User>
    suspend fun addUser(email: String, password: String, role: UserRole): User
    suspend fun updateUserRole(userId: String, role: UserRole)
    suspend fun updateUserPassword(userId: String, newPassword: String)
    suspend fun deleteUser(userId: String)
}