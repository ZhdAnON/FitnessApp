package com.zhdanon.fitnessapp.presentation.admin.workoutsEditor.draft

sealed class RoundsDraft(val type: RoundsTypeUi) {
    data class Fixed(val count: String = "") :
        RoundsDraft(RoundsTypeUi.FIXED)
    data class Range(val from: String = "", val to: String = "") :
        RoundsDraft(RoundsTypeUi.RANGE)
    data class TimeFixed(val duration: String = "") :
        RoundsDraft(RoundsTypeUi.TIME_FIXED)
    data class TimeRange(val from: String = "", val to: String = "") :
        RoundsDraft(RoundsTypeUi.TIME_RANGE)
}

enum class RoundsTypeUi { FIXED, RANGE, TIME_FIXED, TIME_RANGE }