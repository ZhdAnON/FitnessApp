package com.zhdanon.fitnessapp.domain.models.auth

data class User(
    val id: String,
    val email: String,
    val role: UserRole
)