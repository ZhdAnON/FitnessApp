package com.zhdanon.fitnessapp.data.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class TokenPair(
    val access: String,
    val refresh: String
)