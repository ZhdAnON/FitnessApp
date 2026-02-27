package com.zhdanon.fitnessapp.data.mapper

import NutritionProgramDto
import com.zhdanon.fitnessapp.domain.models.nutrition.NutritionProgram

fun NutritionProgramDto.toDomain() = NutritionProgram(
    id = id,
    category = category,
    text = text
)