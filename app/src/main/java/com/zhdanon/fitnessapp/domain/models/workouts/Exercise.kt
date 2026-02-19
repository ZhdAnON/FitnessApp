package com.zhdanon.fitnessapp.domain.models.workouts

data class Exercise(
    val id: String,
    val name: String,
    val muscleGroups: List<String>,
    val technique: String,
    val videoUrl: String? = null
)