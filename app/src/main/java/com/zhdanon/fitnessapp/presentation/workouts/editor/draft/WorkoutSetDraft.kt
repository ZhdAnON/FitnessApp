package com.zhdanon.fitnessapp.presentation.workouts.editor.draft

import com.zhdanon.fitnessapp.domain.models.workouts.ProtocolType

data class WorkoutSetDraft(
    val localId: Long = System.nanoTime(),
    val order: Int = 0,
    val protocol: ProtocolType = ProtocolType.STANDARD,
    val rounds: RoundsDraft = RoundsDraft.Fixed(1),
    val exercises: List<WorkoutExerciseDraft> = emptyList(),
    val note: String = ""
)