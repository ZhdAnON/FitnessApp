package com.zhdanon.fitnessapp.domain.usecases.exercises

import com.zhdanon.fitnessapp.data.repository.ExerciseRepositoryImpl
import com.zhdanon.fitnessapp.domain.models.workouts.Exercise

class GetExerciseByIdUseCase(private val exerciseRep: ExerciseRepositoryImpl) {
    suspend operator fun invoke(exerciseId: String): Exercise =
        exerciseRep.getExerciseById(exerciseId)
}