package com.zhdanon.fitnessapp.domain.usecases.exercises

import com.zhdanon.fitnessapp.domain.models.workouts.Exercise
import com.zhdanon.fitnessapp.domain.repositories.ExerciseRepository

class GetExerciseUseCase(private val exerciseRep: ExerciseRepository) {
    suspend operator fun invoke(): List<Exercise> =
        exerciseRep.getExercises()
}