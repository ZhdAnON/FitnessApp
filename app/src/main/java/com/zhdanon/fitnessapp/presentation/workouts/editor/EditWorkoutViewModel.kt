package com.zhdanon.fitnessapp.presentation.workouts.editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhdanon.fitnessapp.domain.models.workouts.Workout
import com.zhdanon.fitnessapp.domain.usecases.workouts.GetExercisesUseCase
import com.zhdanon.fitnessapp.domain.usecases.workouts.GetWorkoutUseCase
import com.zhdanon.fitnessapp.domain.usecases.workouts.UpdateWorkoutUseCase
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.ExerciseUi
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.WorkoutSetDraft
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.WorkoutUiState
import com.zhdanon.fitnessapp.presentation.workouts.editor.mapper.toDraftState
import com.zhdanon.fitnessapp.presentation.workouts.editor.mapper.toRequest
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class EditWorkoutViewModel(
    private val getWorkoutUseCase: GetWorkoutUseCase,
    private val updateWorkoutUseCase: UpdateWorkoutUseCase,
    private val getExercisesUseCase: GetExercisesUseCase
) : ViewModel() {

    var uiState by mutableStateOf(WorkoutUiState())
        private set

    init {
        loadExercises()
    }

    private fun loadExercises() {
        viewModelScope.launch {
            val list = getExercisesUseCase()
            uiState = uiState.copy(
                exercises = list.map {
                    ExerciseUi(
                        id = it.id,
                        name = it.name
                    )
                }
            )
        }
    }

    // -----------------------------
    // LOAD WORKOUT FOR EDIT
    // -----------------------------
    suspend fun getWorkout(id: String): Workout {
        return getWorkoutUseCase(id)
    }

    fun loadWorkoutForEdit(workout: Workout) {
        val currentExercises = uiState.exercises
        uiState = workout.toDraftState().copy(
            exercises = currentExercises
        )
    }

    // -----------------------------
    // VALIDATION
    // -----------------------------
    private fun validate(): String? {
        return when {
            uiState.title.isBlank() ->
                "Введите название тренировки"

            uiState.sets.isEmpty() ->
                "Добавьте хотя бы один сет"

            uiState.sets.any { it.exercises.isEmpty() } ->
                "В каждом сете должно быть хотя бы одно упражнение"

            else -> null
        }
    }

    // -----------------------------
    // SIMPLE FIELD UPDATES
    // -----------------------------
    fun setTitle(title: String) {
        uiState = uiState.copy(title = title)
    }

    fun setDate(date: LocalDate) {
        uiState = uiState.copy(date = date)
    }

    // -----------------------------
    // SETS MANAGEMENT
    // -----------------------------
    fun addSet() {
        uiState = uiState.copy(
            sets = uiState.sets + WorkoutSetDraft(order = uiState.sets.size)
        )
    }

    fun updateSet(index: Int, set: WorkoutSetDraft) {
        uiState = uiState.copy(
            sets = uiState.sets.toMutableList().apply { this[index] = set }
        )
    }

    // -----------------------------
    // DELETE SET
    // -----------------------------
    fun deleteSet(index: Int) {
        uiState = uiState.copy(
            sets = uiState.sets
                .toMutableList()
                .apply { removeAt(index) }
                .mapIndexed { i, set -> set.copy(order = i) }
        )
    }

    // -----------------------------
    // DELETE EXERCISE
    // -----------------------------
    fun deleteExercise(setIndex: Int, exerciseIndex: Int) {
        uiState = uiState.copy(
            sets = uiState.sets.toMutableList().apply {
                val set = this[setIndex]
                val updatedExercises = set.exercises.toMutableList().apply {
                    removeAt(exerciseIndex)
                }
                this[setIndex] = set.copy(exercises = updatedExercises)
            }
        )
    }

    // -----------------------------
    // EVENTS
    // -----------------------------
    sealed interface EditWorkoutEvent {
        data class Saved(val id: String) : EditWorkoutEvent
    }

    private val _events = MutableSharedFlow<EditWorkoutEvent>()
    val events = _events.asSharedFlow()

    fun updateWorkout() {
        viewModelScope.launch {
            val validation = validate()
            if (validation != null) {
                uiState = uiState.copy(validationError = validation)
                return@launch
            }

            uiState = uiState.copy(isSaving = true)

            try {
                val id = uiState.id ?: error("Workout ID is null")

                val request = uiState.toRequest()
                updateWorkoutUseCase(id, request)
                _events.emit(EditWorkoutEvent.Saved(id))
            } catch (e: Exception) {
                uiState = uiState.copy(validationError = e.message)
            } finally {
                uiState = uiState.copy(isSaving = false)
            }
        }
    }

    // -----------------------------
    // MOVE SETS and EXERCISES
    // -----------------------------
    fun moveSetUp(index: Int) {
        if (index <= 0) return
        val newSets = uiState.sets.toMutableList()
        java.util.Collections.swap(newSets, index, index - 1)

        uiState = uiState.copy(
            sets = newSets.mapIndexed { i, set -> set.copy(order = i) }
        )
    }

    fun moveSetDown(index: Int) {
        if (index >= uiState.sets.lastIndex) return
        val newSets = uiState.sets.toMutableList()
        java.util.Collections.swap(newSets, index, index + 1)

        uiState = uiState.copy(
            sets = newSets.mapIndexed { i, set -> set.copy(order = i) }
        )
    }

    fun moveExerciseUp(setIndex: Int, exerciseIndex: Int) {
        if (exerciseIndex <= 0) return

        val sets = uiState.sets.toMutableList()
        val set = sets[setIndex]

        val newExercises = set.exercises.toMutableList()
        java.util.Collections.swap(newExercises, exerciseIndex, exerciseIndex - 1)

        sets[setIndex] = set.copy(exercises = newExercises)

        uiState = uiState.copy(sets = sets)
    }

    fun moveExerciseDown(setIndex: Int, exerciseIndex: Int) {
        val sets = uiState.sets.toMutableList()
        val set = sets[setIndex]

        if (exerciseIndex >= set.exercises.lastIndex) return

        val newExercises = set.exercises.toMutableList()
        java.util.Collections.swap(newExercises, exerciseIndex, exerciseIndex + 1)

        sets[setIndex] = set.copy(exercises = newExercises)

        uiState = uiState.copy(sets = sets)
    }
}