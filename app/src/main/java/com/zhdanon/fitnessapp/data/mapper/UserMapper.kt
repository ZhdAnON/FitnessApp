package com.zhdanon.fitnessapp.data.mapper

import com.zhdanon.fitnessapp.data.dto.auth.UserDto
import com.zhdanon.fitnessapp.domain.models.auth.User
import com.zhdanon.fitnessapp.domain.models.auth.UserRole

fun UserDto.toDomain() = User(
    id = id,
    email = email,
    role = UserRole.valueOf(role.uppercase())
)