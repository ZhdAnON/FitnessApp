package com.zhdanon.fitnessapp.presentation.workouts.workoutsList.components

import com.zhdanon.fitnessapp.domain.models.workouts.Workout

data class WorkoutsUiState(
    val workouts: List<Workout> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)