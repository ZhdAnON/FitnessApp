package com.zhdanon.fitnessapp.domain.usecases.nutrition

import com.zhdanon.fitnessapp.domain.models.nutrition.NutritionProgram
import com.zhdanon.fitnessapp.domain.repositories.NutritionRepository

class GetNutritionProgramDetailsUseCase(
    private val repo: NutritionRepository
) {
    suspend operator fun invoke(id: String): NutritionProgram {
        return repo.getProgramDetails(id)
    }
}