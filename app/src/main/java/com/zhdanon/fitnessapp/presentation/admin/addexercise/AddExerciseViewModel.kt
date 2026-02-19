package com.zhdanon.fitnessapp.presentation.admin.addexercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhdanon.fitnessapp.data.dto.workouts.ExerciseRequest
import com.zhdanon.fitnessapp.domain.usecases.exercises.AddExerciseUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class AddExerciseViewModel(
    private val addExerciseUseCase: AddExerciseUseCase
) : ViewModel() {

    var name by mutableStateOf("")
    var muscleGroups by mutableStateOf("")
    var technique by mutableStateOf("")
    var videoUrl by mutableStateOf("")
    var error by mutableStateOf<String?>(null)

    private val _events = MutableSharedFlow<AddExerciseEvent>()
    val events = _events.asSharedFlow()

    sealed interface AddExerciseEvent {
        data object Saved : AddExerciseEvent
    }

    fun save() {
        viewModelScope.launch {
            try {
                addExerciseUseCase(
                    ExerciseRequest(
                        name = name,
                        muscleGroups = muscleGroups.split(",").map { it.trim() },
                        technique = technique,
                        videoUrl = videoUrl.ifBlank { null }
                    )
                )
                _events.emit(AddExerciseEvent.Saved)
            } catch (e: Exception) {
                error = e.message
            }
        }
    }
}