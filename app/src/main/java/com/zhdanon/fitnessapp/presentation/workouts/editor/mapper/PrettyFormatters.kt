package com.zhdanon.fitnessapp.presentation.workouts.editor.mapper

import com.zhdanon.fitnessapp.domain.models.workouts.Reps
import com.zhdanon.fitnessapp.domain.models.workouts.Rounds

fun Rounds.toPrettyString(): String = when (this) {
    is Rounds.Fixed -> "$count кругов"
    is Rounds.Range -> "$from–$to кругов"
    is Rounds.TimeFixed -> "$duration мин"
    is Rounds.TimeRange -> "$from–$to мин"
}

fun Reps.toPrettyString(): String = when (this) {
    is Reps.Fixed -> count.toString()
    is Reps.Range -> "$from–$to"
    is Reps.TimeFixed -> "$duration сек"
    is Reps.TimeRange -> "$from–$to сек"
    Reps.None -> ""
}