package com.zhdanon.fitnessapp.domain.usecases.workouts

import com.zhdanon.fitnessapp.domain.models.workouts.Workout
import com.zhdanon.fitnessapp.domain.repositories.WorkoutRepository

class GetAllWorkoutsUseCase(private val workoutRep: WorkoutRepository) {
    suspend operator fun invoke(): List<Workout> =
        workoutRep.getAllWorkouts()
}