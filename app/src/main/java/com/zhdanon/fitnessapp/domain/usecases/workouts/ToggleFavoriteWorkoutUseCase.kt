package com.zhdanon.fitnessapp.domain.usecases.workouts

import com.zhdanon.fitnessapp.domain.models.workouts.Workout
import com.zhdanon.fitnessapp.domain.repositories.WorkoutRepository

class ToggleFavoriteWorkoutUseCase(
    private val workoutRep: WorkoutRepository
) {
    suspend operator fun invoke(id: String): Workout {
        return workoutRep.toggleFavorite(id)
    }
}