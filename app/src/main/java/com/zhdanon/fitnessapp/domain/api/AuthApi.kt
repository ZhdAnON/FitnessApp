package com.zhdanon.fitnessapp.domain.api

import com.zhdanon.fitnessapp.data.dto.auth.AuthResponseDto
import com.zhdanon.fitnessapp.data.dto.auth.ChangePasswordRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.LoginRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.RegisterRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.UserDto

interface AuthApi {
    suspend fun register(request: RegisterRequestDto): AuthResponseDto
    suspend fun login(request: LoginRequestDto): AuthResponseDto
    suspend fun changeOwnPassword(request: ChangePasswordRequestDto)
    suspend fun getProfile(): UserDto
}