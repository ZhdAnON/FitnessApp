package com.zhdanon.fitnessapp.domain.usecases.exercises

import com.zhdanon.fitnessapp.data.dto.workouts.ExerciseRequest
import com.zhdanon.fitnessapp.domain.models.workouts.Exercise
import com.zhdanon.fitnessapp.domain.repositories.ExerciseRepository

class UpdateExerciseUseCase(
    private val repo: ExerciseRepository
) {
    suspend operator fun invoke(id: String, request: ExerciseRequest): Exercise {
        return repo.updateExercise(id, request)
    }
}