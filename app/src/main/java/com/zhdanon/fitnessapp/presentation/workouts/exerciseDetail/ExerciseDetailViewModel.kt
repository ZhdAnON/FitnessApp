package com.zhdanon.fitnessapp.presentation.workouts.exerciseDetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhdanon.fitnessapp.domain.models.workouts.Exercise
import com.zhdanon.fitnessapp.domain.usecases.exercises.GetExerciseByIdUseCase
import kotlinx.coroutines.launch

class ExerciseDetailViewModel(
    private val exercisesUseCase: GetExerciseByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var exercise by mutableStateOf<Exercise?>(null)
    var isLoading by mutableStateOf(true)
    var error by mutableStateOf<String?>(null)

    private val id = savedStateHandle.get<String>("id")!!

    init {
        load()
    }

    private fun load() {
        println("ExerciseDetailViewModel: id = $id")
        viewModelScope.launch {
            try {
                exercise = exercisesUseCase(id)
                isLoading = false
            } catch (e: Exception) {
                error = e.message
                isLoading = false
            }
        }
    }
}