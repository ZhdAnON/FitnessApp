package com.zhdanon.fitnessapp.domain.models.nutrition

data class NutritionProgram(
    val id: String,
    val category: NutritionCategory,
    val text: String
)