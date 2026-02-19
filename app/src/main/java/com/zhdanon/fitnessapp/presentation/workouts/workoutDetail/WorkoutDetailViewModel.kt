package com.zhdanon.fitnessapp.presentation.workouts.workoutDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhdanon.fitnessapp.domain.usecases.workouts.GetExercisesUseCase
import com.zhdanon.fitnessapp.domain.usecases.workouts.GetWorkoutUseCase
import kotlinx.coroutines.launch

class WorkoutDetailViewModel(
    private val getWorkoutUseCase: GetWorkoutUseCase,
    private val getExercisesUseCase: GetExercisesUseCase
) : ViewModel() {

    var uiState by mutableStateOf(WorkoutDetailUiState())
        private set

    fun loadWorkout(id: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                val workout = getWorkoutUseCase(id)
                val exercises = getExercisesUseCase().associateBy { it.id }

                uiState = uiState.copy(
                    workout = workout,
                    exercises = exercises,
                    error = null
                )
            } catch (e: Exception) {
                uiState = uiState.copy(error = e.message)
            } finally {
                uiState = uiState.copy(isLoading = false)
            }
        }
    }
}