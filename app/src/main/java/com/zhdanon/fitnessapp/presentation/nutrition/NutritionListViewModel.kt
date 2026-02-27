package com.zhdanon.fitnessapp.presentation.nutrition

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhdanon.fitnessapp.domain.models.nutrition.NutritionProgram
import com.zhdanon.fitnessapp.domain.usecases.nutrition.GetAllNutritionProgramsUseCase
import kotlinx.coroutines.launch

class NutritionListViewModel(
    private val getAllUseCase: GetAllNutritionProgramsUseCase
) : ViewModel() {

    var programs by mutableStateOf<List<NutritionProgram>>(emptyList())
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    init {
        loadPrograms()
    }

    fun loadPrograms() {
        viewModelScope.launch {
            try {
                isLoading = true
                programs = getAllUseCase()
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }
}