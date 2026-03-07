package com.zhdanon.fitnessapp.domain.usecases.exercises

import com.zhdanon.fitnessapp.domain.repositories.ExerciseRepository

class DeleteExerciseUseCase(
    private val repo: ExerciseRepository
) {
    suspend operator fun invoke(id: String) = repo.deleteExercise(id)
}