package com.zhdanon.fitnessapp.data.dto.workouts

import kotlinx.serialization.Serializable

@Serializable
data class WorkoutDto(
    val id: String,
    val date: String,
    val title: String,
    val sets: List<WorkoutSetDto>
)