package com.zhdanon.fitnessapp.presentation.admin.addexercise

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhdanon.fitnessapp.data.dto.workouts.ExerciseRequest
import com.zhdanon.fitnessapp.domain.models.workouts.Muscle
import com.zhdanon.fitnessapp.domain.models.workouts.MuscleCategory
import com.zhdanon.fitnessapp.domain.usecases.exercises.GetExerciseByIdUseCase
import com.zhdanon.fitnessapp.domain.usecases.exercises.UpdateExerciseUseCase
import com.zhdanon.fitnessapp.domain.usecases.exercises.UploadExerciseVideoUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.File

class EditExerciseViewModel(
    private val getExerciseUseCase: GetExerciseByIdUseCase,
    private val updateExerciseUseCase: UpdateExerciseUseCase,
    private val uploadExerciseVideoUseCase: UploadExerciseVideoUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val exerciseId: String = savedStateHandle["exerciseId"]!!

    var name by mutableStateOf("")
    var selectedMuscles by mutableStateOf<List<Muscle>>(emptyList())
    var technique by mutableStateOf("")
    var videoUrl by mutableStateOf("")
    var error by mutableStateOf<String?>(null)

    var selectedVideoFile: File? = null
    var selectedVideoName by mutableStateOf<String?>(null)
    var selectedVideoUri by mutableStateOf<Uri?>(null)

    private val _events = MutableSharedFlow<EditExerciseEvent>()
    val events = _events.asSharedFlow()

    sealed interface EditExerciseEvent {
        data object Saved : EditExerciseEvent
    }

    init {
        loadExercise()
    }

    private fun loadExercise() {
        viewModelScope.launch {
            try {
                val ex = getExerciseUseCase(exerciseId)
                name = ex.name
                selectedMuscles = ex.muscleGroups.map { MuscleCategory.fromTitle(it) }
                technique = ex.technique
                videoUrl = ex.videoUrl ?: ""
            } catch (e: Exception) {
                error = e.message
            }
        }
    }

    fun onVideoSelected(uri: Uri, file: File, name: String) {
        selectedVideoUri = uri
        selectedVideoFile = file
        selectedVideoName = name
    }

    private suspend fun uploadVideoIfNeeded() {
        val file = selectedVideoFile ?: return
        try {
            val url = uploadExerciseVideoUseCase(exerciseId, file)
            videoUrl = url
        } catch (e: Exception) {
            error = e.message
        }
    }

    fun save() {
        viewModelScope.launch {
            try {
                updateExerciseUseCase(
                    id = exerciseId,
                    request = ExerciseRequest(
                        name = name,
                        muscleGroups = selectedMuscles.map { it.title },
                        technique = technique,
                        videoUrl = videoUrl.ifBlank { null }
                    )
                )

                uploadVideoIfNeeded()

                _events.emit(EditExerciseEvent.Saved)
            } catch (e: Exception) {
                error = e.message
            }
        }
    }
}