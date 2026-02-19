package com.zhdanon.fitnessapp.data.dto.workouts

import com.zhdanon.fitnessapp.domain.models.workouts.ProtocolType
import kotlinx.serialization.Serializable

@Serializable
data class WorkoutSetRequest(
    val order: Int,
    val protocol: ProtocolType,
    val rounds: RoundsRequest,
    val exercises: List<SetExerciseRequest>,
    val note: String? = null
)