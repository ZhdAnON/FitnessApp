package com.zhdanon.fitnessapp.domain.models.auth

data class AuthToken(
    val accessToken: String,
    val refreshToken: String
)