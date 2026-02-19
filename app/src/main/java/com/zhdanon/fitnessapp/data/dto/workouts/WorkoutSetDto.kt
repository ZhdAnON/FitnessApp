package com.zhdanon.fitnessapp.data.dto.workouts

import com.zhdanon.fitnessapp.domain.models.workouts.ProtocolType
import kotlinx.serialization.Serializable

@Serializable
data class WorkoutSetDto(
    val id: String,
    val order: Int,
    val protocol: ProtocolType,
    val rounds: RoundsDto,
    val exercises: List<SetExerciseDto>,
    val note: String? = null
)