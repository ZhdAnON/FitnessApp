package com.zhdanon.fitnessapp.data.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordRequestDto(
    val oldPassword: String,
    val newPassword: String
)