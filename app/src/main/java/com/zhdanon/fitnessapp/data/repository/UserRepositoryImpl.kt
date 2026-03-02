package com.zhdanon.fitnessapp.data.repository

import com.zhdanon.fitnessapp.domain.api.UserApi
import com.zhdanon.fitnessapp.data.dto.auth.AddUserRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.UpdatePasswordRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.UpdateRoleRequestDto
import com.zhdanon.fitnessapp.data.mapper.toDomain
import com.zhdanon.fitnessapp.domain.api.ApiCallExecutor
import com.zhdanon.fitnessapp.domain.models.auth.User
import com.zhdanon.fitnessapp.domain.models.auth.UserRole
import com.zhdanon.fitnessapp.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val userApi: UserApi,
    private val executor: ApiCallExecutor
) : UserRepository {

    override suspend fun getAllUsers(): List<User> =
        executor.execute { userApi.getAllUsers() }
            ?.map { it.toDomain() }
            ?: emptyList()

    override suspend fun addUser(email: String, password: String, role: UserRole): User =
        executor.execute {
            userApi.addUser(
                AddUserRequestDto(
                    email = email,
                    password = password,
                    role = role.name.lowercase()
                )
            )
        }!!.toDomain()

    override suspend fun updateUserRole(userId: String, role: UserRole) {
        executor.execute {
            userApi.updateUserRole(
                userId,
                UpdateRoleRequestDto(role.name.lowercase())
            )
        }
    }

    override suspend fun updateUserPassword(userId: String, newPassword: String) {
        executor.execute {
            userApi.updateUserPassword(
                userId,
                UpdatePasswordRequestDto(newPassword)
            )
        }
    }

    override suspend fun changeOwnPassword(newPassword: String) {
        executor.execute {
            userApi.changeOwnPassword(UpdatePasswordRequestDto(newPassword))
        }
    }

    override suspend fun deleteUser(userId: String) {
        executor.execute { userApi.deleteUser(userId) }
    }
}