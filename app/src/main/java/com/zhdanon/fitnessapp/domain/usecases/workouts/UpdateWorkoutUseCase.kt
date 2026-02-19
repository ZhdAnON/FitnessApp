package com.zhdanon.fitnessapp.domain.usecases.workouts

import com.zhdanon.fitnessapp.data.dto.workouts.WorkoutRequest
import com.zhdanon.fitnessapp.domain.models.workouts.Workout
import com.zhdanon.fitnessapp.domain.repositories.WorkoutRepository

class UpdateWorkoutUseCase(
    private val workoutRep: WorkoutRepository
) {
    suspend operator fun invoke(id: String, workout: WorkoutRequest): Workout {
        val result = workoutRep.updateWorkout(id, workout)
        return result
    }
}