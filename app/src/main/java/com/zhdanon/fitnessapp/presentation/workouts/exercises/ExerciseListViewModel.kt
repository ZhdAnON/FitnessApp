package com.zhdanon.fitnessapp.presentation.workouts.exercises

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhdanon.fitnessapp.domain.models.workouts.Exercise
import com.zhdanon.fitnessapp.domain.usecases.exercises.GetExerciseUseCase
import kotlinx.coroutines.launch

class ExerciseListViewModel(
    private val getExerciseUseCase: GetExerciseUseCase
) : ViewModel() {

    var exercises by mutableStateOf<List<Exercise>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    init {
        loadExercises()
    }

    fun loadExercises() {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                exercises = getExerciseUseCase()
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }
}