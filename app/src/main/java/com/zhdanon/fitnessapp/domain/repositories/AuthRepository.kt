package com.zhdanon.fitnessapp.domain.repositories

import com.zhdanon.fitnessapp.domain.models.auth.AuthToken
import com.zhdanon.fitnessapp.domain.models.auth.User

interface AuthRepository {
    suspend fun register(email: String, password: String): Pair<User, AuthToken>
    suspend fun login(email: String, password: String): Pair<User, AuthToken>
    suspend fun changeOwnPassword(oldPassword: String, newPassword: String)
    suspend fun logout()
    fun getCurrentUser(): User?
    suspend fun getCurrentToken(): AuthToken?
    suspend fun fetchProfile(): User
    suspend fun refreshToken(): Boolean
}