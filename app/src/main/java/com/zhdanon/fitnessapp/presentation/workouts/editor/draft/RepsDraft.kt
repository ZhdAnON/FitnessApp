package com.zhdanon.fitnessapp.presentation.workouts.editor.draft

sealed class RepsDraft(val type: RepsTypeUi) {
    data class Fixed(val count: String = "") :
        RepsDraft(RepsTypeUi.FIXED)
    data class Range(val from: String = "", val to: String = "") :
        RepsDraft(RepsTypeUi.RANGE)
    data class TimeFixed(val duration: String = "") :
        RepsDraft(RepsTypeUi.TIME_FIXED)
    data class TimeRange(val from: String = "", val to: String = "") :
        RepsDraft(RepsTypeUi.TIME_RANGE)
    data object None :
        RepsDraft(RepsTypeUi.MAX)
}

enum class RepsTypeUi { FIXED, RANGE, TIME_FIXED, TIME_RANGE, MAX }