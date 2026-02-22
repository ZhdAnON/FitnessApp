package com.zhdanon.fitnessapp.presentation.user

import com.zhdanon.fitnessapp.domain.models.workouts.Workout

data class UserUiState(
    val workouts: List<Workout> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val message: String? = null
)