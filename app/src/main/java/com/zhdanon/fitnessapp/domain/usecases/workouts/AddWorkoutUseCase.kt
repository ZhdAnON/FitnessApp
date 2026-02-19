package com.zhdanon.fitnessapp.domain.usecases.workouts

import com.zhdanon.fitnessapp.data.dto.workouts.CreatedWorkoutResponse
import com.zhdanon.fitnessapp.data.dto.workouts.WorkoutRequest
import com.zhdanon.fitnessapp.domain.models.workouts.Workout
import com.zhdanon.fitnessapp.domain.repositories.WorkoutRepository

class AddWorkoutUseCase(
    private val workoutRep: WorkoutRepository
) {
    suspend operator fun invoke(workout: WorkoutRequest): CreatedWorkoutResponse {
        return workoutRep.addWorkout(workout)
    }
}