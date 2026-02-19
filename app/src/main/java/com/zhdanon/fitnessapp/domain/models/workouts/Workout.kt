package com.zhdanon.fitnessapp.domain.models.workouts

import java.time.LocalDate

data class Workout(
    val id: String,
    val date: LocalDate,
    val title: String,
    val sets: List<WorkoutSet>
)