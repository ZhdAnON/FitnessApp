package com.zhdanon.fitnessapp.presentation.nutrition

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhdanon.fitnessapp.domain.models.nutrition.NutritionProgram
import com.zhdanon.fitnessapp.domain.usecases.nutrition.GetNutritionProgramDetailsUseCase
import kotlinx.coroutines.launch

class NutritionDetailViewModel(
    private val getDetailsUseCase: GetNutritionProgramDetailsUseCase
) : ViewModel() {

    var program by mutableStateOf<NutritionProgram?>(null)
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)

    fun loadProgram(id: String) {
        viewModelScope.launch {
            try {
                isLoading = true
                program = getDetailsUseCase(id)
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }
}