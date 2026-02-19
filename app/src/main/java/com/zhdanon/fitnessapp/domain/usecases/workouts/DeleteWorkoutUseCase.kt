package com.zhdanon.fitnessapp.domain.usecases.workouts

import com.zhdanon.fitnessapp.domain.repositories.WorkoutRepository

class DeleteWorkoutUseCase(
    private val workoutRep: WorkoutRepository
) {
    suspend operator fun invoke(id: String) {
        workoutRep.deleteWorkout(id)
    }
}