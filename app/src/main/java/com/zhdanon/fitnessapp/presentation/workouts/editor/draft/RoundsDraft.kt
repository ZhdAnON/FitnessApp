package com.zhdanon.fitnessapp.presentation.workouts.editor.draft

sealed class RoundsDraft(val type: RoundsTypeUi) {

    data class Fixed(val count: Int) :
        RoundsDraft(RoundsTypeUi.FIXED)

    data class Range(val from: Int, val to: Int) :
        RoundsDraft(RoundsTypeUi.RANGE)

    data class TimeFixed(val duration: Int) :
        RoundsDraft(RoundsTypeUi.TIME_FIXED)

    data class TimeRange(val from: Int, val to: Int) :
        RoundsDraft(RoundsTypeUi.TIME_RANGE)
}

enum class RoundsTypeUi { FIXED, RANGE, TIME_FIXED, TIME_RANGE }