package com.zhdanon.fitnessapp.domain.repositories

import CreateNutritionProgramRequest
import com.zhdanon.fitnessapp.domain.models.nutrition.NutritionProgram

interface NutritionRepository {

    suspend fun getAllPrograms(): List<NutritionProgram>

    suspend fun getProgramDetails(id: String): NutritionProgram

    suspend fun createProgram(request: CreateNutritionProgramRequest): NutritionProgram

    suspend fun deleteProgram(id: String)
}