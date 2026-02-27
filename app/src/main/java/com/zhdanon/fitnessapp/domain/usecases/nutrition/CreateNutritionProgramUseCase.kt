package com.zhdanon.fitnessapp.domain.usecases.nutrition

import CreateNutritionProgramRequest
import com.zhdanon.fitnessapp.domain.models.nutrition.NutritionCategory
import com.zhdanon.fitnessapp.domain.models.nutrition.NutritionProgram
import com.zhdanon.fitnessapp.domain.repositories.NutritionRepository

class CreateNutritionProgramUseCase(
    private val repo: NutritionRepository
) {
    suspend operator fun invoke(
        category: NutritionCategory,
        text: String
    ): NutritionProgram {
        val request = CreateNutritionProgramRequest(category, text)
        return repo.createProgram(request)
    }
}