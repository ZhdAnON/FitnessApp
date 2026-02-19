package com.zhdanon.fitnessapp.domain.models.workouts

data class WorkoutExercise(
    val id: String,
    val exerciseId: String,
    val reps: Reps,
    val note: String?
)