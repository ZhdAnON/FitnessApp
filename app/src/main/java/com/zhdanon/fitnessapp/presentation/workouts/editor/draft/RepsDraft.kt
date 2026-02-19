package com.zhdanon.fitnessapp.presentation.workouts.editor.draft

sealed class RepsDraft(val type: RepsTypeUi) {

    data class Fixed(val count: Int) :
        RepsDraft(RepsTypeUi.FIXED)

    data class Range(val from: Int, val to: Int) :
        RepsDraft(RepsTypeUi.RANGE)

    data class TimeFixed(val duration: Int) :
        RepsDraft(RepsTypeUi.TIME_FIXED)

    data class TimeRange(val from: Int, val to: Int) :
        RepsDraft(RepsTypeUi.TIME_RANGE)

    data object None :
        RepsDraft(RepsTypeUi.MAX)
}

enum class RepsTypeUi { FIXED, RANGE, TIME_FIXED, TIME_RANGE, MAX }