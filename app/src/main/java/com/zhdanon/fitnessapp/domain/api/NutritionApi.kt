package com.zhdanon.fitnessapp.domain.api

import CreateNutritionProgramRequest
import NutritionProgramDto

interface NutritionApi {

    suspend fun getAllPrograms(): List<NutritionProgramDto>

    suspend fun getProgramDetails(id: String): NutritionProgramDto

    suspend fun createProgram(request: CreateNutritionProgramRequest): NutritionProgramDto

    suspend fun deleteProgram(id: String)
}