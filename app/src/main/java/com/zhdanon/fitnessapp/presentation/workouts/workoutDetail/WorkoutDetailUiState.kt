package com.zhdanon.fitnessapp.presentation.workouts.workoutDetail

import com.zhdanon.fitnessapp.domain.models.workouts.Exercise
import com.zhdanon.fitnessapp.domain.models.workouts.Workout

data class WorkoutDetailUiState(
    val workout: Workout? = null,
    val exercises: Map<String, Exercise> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null
)