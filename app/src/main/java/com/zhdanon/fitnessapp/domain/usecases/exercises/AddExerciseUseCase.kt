package com.zhdanon.fitnessapp.domain.usecases.exercises

import com.zhdanon.fitnessapp.data.dto.workouts.ExerciseRequest
import com.zhdanon.fitnessapp.data.repository.ExerciseRepositoryImpl
import com.zhdanon.fitnessapp.domain.models.workouts.Exercise

class AddExerciseUseCase(
    private val exerciseRep: ExerciseRepositoryImpl
) {
    suspend operator fun invoke(exercise: ExerciseRequest): Exercise {
        return exerciseRep.createExercise(exercise)
    }
}