package com.zhdanon.fitnessapp.domain.models.workouts

data class WorkoutSet(
    val id: String,
    val order: Int,
    val protocol: ProtocolType,
    val rounds: Rounds,
    val exercises: List<WorkoutExercise>,
    val note: String?
)