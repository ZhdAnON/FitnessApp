package com.zhdanon.fitnessapp.domain.usecases.nutrition

import com.zhdanon.fitnessapp.domain.models.nutrition.NutritionProgram
import com.zhdanon.fitnessapp.domain.repositories.NutritionRepository

class GetAllNutritionProgramsUseCase(
    private val repo: NutritionRepository
) {
    suspend operator fun invoke(): List<NutritionProgram> {
        return repo.getAllPrograms()
    }
}