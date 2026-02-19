package com.zhdanon.fitnessapp.data.dto.workouts

import RepsDto
import kotlinx.serialization.Serializable

@Serializable
data class SetExerciseDto(
    val id: String,
    val exerciseId: String,
    val reps: RepsDto,
    val note: String? = null
)