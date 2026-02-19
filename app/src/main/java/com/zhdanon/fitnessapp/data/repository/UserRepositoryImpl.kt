package com.zhdanon.fitnessapp.data.repository

import com.zhdanon.fitnessapp.domain.api.UserApi
import com.zhdanon.fitnessapp.data.dto.auth.AddUserRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.UpdatePasswordRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.UpdateRoleRequestDto
import com.zhdanon.fitnessapp.data.mapper.toDomain
import com.zhdanon.fitnessapp.domain.models.auth.User
import com.zhdanon.fitnessapp.domain.models.auth.UserRole
import com.zhdanon.fitnessapp.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val userApi: UserApi
) : UserRepository {

    override suspend fun getAllUsers(): List<User> =
        userApi.getAllUsers().map { it.toDomain() }

    override suspend fun addUser(email: String, password: String, role: UserRole): User {
        val dto = AddUserRequestDto(
            email = email,
            password = password,
            role = role.name.lowercase()
        )
        return userApi.addUser(dto).toDomain()
    }

    override suspend fun updateUserRole(userId: String, role: UserRole) {
        userApi.updateUserRole(
            userId,
            UpdateRoleRequestDto(role.name.lowercase())
        )
    }

    override suspend fun updateUserPassword(userId: String, newPassword: String) {
        userApi.updateUserPassword(
            userId,
            UpdatePasswordRequestDto(newPassword)
        )
    }

    override suspend fun deleteUser(userId: String) {
        userApi.deleteUser(userId)
    }
}