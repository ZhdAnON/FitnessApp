package com.zhdanon.fitnessapp.data.repository

import CreateNutritionProgramRequest
import com.zhdanon.fitnessapp.data.mapper.toDomain
import com.zhdanon.fitnessapp.domain.api.NutritionApi
import com.zhdanon.fitnessapp.domain.models.nutrition.NutritionProgram
import com.zhdanon.fitnessapp.domain.repositories.NutritionRepository

class NutritionRepositoryImpl(
    private val api: NutritionApi
) : NutritionRepository {

    override suspend fun getAllPrograms(): List<NutritionProgram> =
        api.getAllPrograms().map { it.toDomain() }

    override suspend fun getProgramDetails(id: String): NutritionProgram =
        api.getProgramDetails(id).toDomain()

    override suspend fun createProgram(request: CreateNutritionProgramRequest): NutritionProgram =
        api.createProgram(request).toDomain()

    override suspend fun deleteProgram(id: String) {
        api.deleteProgram(id)
    }
}