package com.zhdanon.fitnessapp.presentation.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhdanon.fitnessapp.domain.models.workouts.Workout
import com.zhdanon.fitnessapp.domain.usecases.auth.ChangeOwnPasswordUseCase
import com.zhdanon.fitnessapp.domain.usecases.workouts.GetAllWorkoutsUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate

class UserViewModel (
    private val getAllWorkoutsUseCase: GetAllWorkoutsUseCase,
    private val changeOwnPasswordUseCase: ChangeOwnPasswordUseCase
): ViewModel() {

    var uiState by mutableStateOf(UserUiState())
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

    fun changeOwnPassword(newPassword: String) {
        viewModelScope.launch {
            try {
                changeOwnPasswordUseCase(newPassword)
                uiState = uiState.copy(message = "Пароль успешно изменён")
            } catch (e: Exception) {
                uiState = uiState.copy(error = e.message)
            }
        }
    }
}