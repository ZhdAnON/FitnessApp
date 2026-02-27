package com.zhdanon.fitnessapp.presentation.admin.nutrition

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhdanon.fitnessapp.domain.models.nutrition.NutritionCategory
import com.zhdanon.fitnessapp.domain.usecases.nutrition.CreateNutritionProgramUseCase
import kotlinx.coroutines.launch

class CreateNutritionViewModel(
    private val createUseCase: CreateNutritionProgramUseCase
) : ViewModel() {

    var selectedCategory by mutableStateOf<NutritionCategory?>(null)
    var text by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    var success by mutableStateOf(false)

    fun createProgram() {
        val category = selectedCategory ?: run {
            error = "Выберите категорию"
            return
        }

        if (text.isBlank()) {
            error = "Введите текст программы"
            return
        }

        viewModelScope.launch {
            try {
                isLoading = true
                createUseCase(category, text)
                success = true
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }
}