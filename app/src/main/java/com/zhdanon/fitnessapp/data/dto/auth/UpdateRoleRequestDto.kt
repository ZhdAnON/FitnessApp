package com.zhdanon.fitnessapp.data.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class UpdateRoleRequestDto(
    val role: String
)