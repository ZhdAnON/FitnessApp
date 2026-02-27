package com.zhdanon.fitnessapp.domain.usecases.nutrition

import com.zhdanon.fitnessapp.domain.repositories.NutritionRepository

class DeleteNutritionProgramUseCase(
    private val repo: NutritionRepository
) {
    suspend operator fun invoke(id: String) {
        repo.deleteProgram(id)
    }
}