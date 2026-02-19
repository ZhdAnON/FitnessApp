package com.zhdanon.fitnessapp.presentation.workouts.editor.draft

import java.time.LocalDate

data class WorkoutUiState(
    val id: String? = null,
    val date: LocalDate = LocalDate.now(),
    val title: String = "",
    val sets: List<WorkoutSetDraft> = emptyList(),
    val isSaving: Boolean = false,
    val savedWorkoutId: String? = null,
    val error: String? = null,
    val validationError: String? = null
)