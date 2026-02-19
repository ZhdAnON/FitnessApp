package com.zhdanon.fitnessapp.presentation.workouts.editor.draft

data class WorkoutExerciseDraft(
    val localId: Long = System.nanoTime(),
    val exerciseId: String = "",
    val reps: RepsDraft = RepsDraft.Fixed(1),
    val note: String = ""
)