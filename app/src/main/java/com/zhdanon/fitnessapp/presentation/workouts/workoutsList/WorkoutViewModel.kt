package com.zhdanon.fitnessapp.presentation.workouts.workoutsList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhdanon.fitnessapp.domain.models.workouts.Workout
import com.zhdanon.fitnessapp.domain.usecases.workouts.DeleteWorkoutUseCase
import com.zhdanon.fitnessapp.domain.usecases.workouts.GetAllWorkoutsUseCase
import com.zhdanon.fitnessapp.domain.usecases.workouts.ToggleFavoriteWorkoutUseCase
import com.zhdanon.fitnessapp.presentation.workouts.workoutsList.components.WorkoutsUiState
import kotlinx.coroutines.launch
import java.time.LocalDate

class WorkoutViewModel(
    private val getAllWorkoutsUseCase: GetAllWorkoutsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteWorkoutUseCase,
    private val deleteWorkoutUseCase: DeleteWorkoutUseCase
) : ViewModel() {
    var uiState by mutableStateOf(WorkoutsUiState())
        private set

    val workoutDates: List<LocalDate>
        get() = uiState.workouts.map { it.date }.distinct()

    var selectedDate by mutableStateOf<LocalDate?>(null)
        private set

    val workoutsOfSelectedDate: List<Workout>
        get() = uiState.workouts.filter { it.date == selectedDate }

    init {
        loadWorkouts()
    }

    fun loadWorkouts() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)

            try {
                val workouts = getAllWorkoutsUseCase()
                uiState = uiState.copy(workouts = workouts, error = null)

            } catch (e: Exception) {
                uiState = uiState.copy(error = e.message)

            } finally {
                uiState = uiState.copy(isLoading = false)
            }
        }
    }

    fun onDateSelected(date: LocalDate) {
        selectedDate = date
    }

    fun deleteWorkout(id: String) {
        viewModelScope.launch {
            try {
                deleteWorkoutUseCase(id)
                loadWorkouts()
            } catch (e: Exception) {
                uiState = uiState.copy(error = e.message)
            }
        }
    }

    fun toggleFavorite(id: String) {
        viewModelScope.launch {
            try {
                toggleFavoriteUseCase(id)
                loadWorkouts()
            } catch (e: Exception) {
                uiState = uiState.copy(error = e.message)
            }
        }
    }
}