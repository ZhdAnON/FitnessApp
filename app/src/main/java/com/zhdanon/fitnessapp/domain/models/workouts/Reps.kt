package com.zhdanon.fitnessapp.domain.models.workouts

sealed class Reps {
    data class Fixed(val count: Int) : Reps()
    data class Range(val from: Int, val to: Int) : Reps()
    data class TimeFixed(val duration: Int) : Reps()
    data class TimeRange(val from: Int, val to: Int) : Reps()
    data object None : Reps()
}