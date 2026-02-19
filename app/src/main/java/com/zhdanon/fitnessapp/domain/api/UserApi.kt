package com.zhdanon.fitnessapp.domain.api

import com.zhdanon.fitnessapp.data.dto.auth.AddUserRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.UpdatePasswordRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.UpdateRoleRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.UserDto

interface UserApi {
    suspend fun getAllUsers(): List<UserDto>
    suspend fun addUser(request: AddUserRequestDto): UserDto
    suspend fun updateUserRole(userId: String, request: UpdateRoleRequestDto)
    suspend fun updateUserPassword(userId: String, request: UpdatePasswordRequestDto)
    suspend fun deleteUser(userId: String)
}