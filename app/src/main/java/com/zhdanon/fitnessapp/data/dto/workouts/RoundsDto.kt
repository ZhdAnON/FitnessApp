package com.zhdanon.fitnessapp.data.dto.workouts

import kotlinx.serialization.Serializable

@Serializable
data class RoundsDto(
    val type: String,
    val count: Int? = null,
    val from: Int? = null,
    val to: Int? = null,
    val duration: Int? = null
)