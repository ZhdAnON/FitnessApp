package com.zhdanon.fitnessapp.domain.usecases.workouts

import com.zhdanon.fitnessapp.domain.models.workouts.Exercise
import com.zhdanon.fitnessapp.domain.repositories.ExerciseRepository

class GetExercisesUseCase(
    private val exerciseRep: ExerciseRepository
) {
    suspend operator fun invoke(): List<Exercise> =
        exerciseRep.getExercises()
}