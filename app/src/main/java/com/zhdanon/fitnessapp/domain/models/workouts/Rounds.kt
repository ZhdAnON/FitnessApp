package com.zhdanon.fitnessapp.domain.models.workouts

sealed class Rounds {
    data class Fixed(val count: Int) : Rounds()
    data class Range(val from: Int, val to: Int) : Rounds()
    data class TimeFixed(val duration: Int) : Rounds()
    data class TimeRange(val from: Int, val to: Int) : Rounds()
}