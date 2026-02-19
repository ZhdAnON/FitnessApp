package com.zhdanon.fitnessapp.domain.usecases.workouts

import com.zhdanon.fitnessapp.data.repository.ExerciseRepositoryImpl
import com.zhdanon.fitnessapp.domain.models.workouts.Exercise

class GetExercisesUseCase(
    private val exerciseRep: ExerciseRepositoryImpl
) {
    suspend operator fun invoke(): List<Exercise> =
        exerciseRep.getExercises()
}