package com.zhdanon.fitnessapp.presentation.admin.addexercise

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhdanon.fitnessapp.data.dto.workouts.ExerciseRequest
import com.zhdanon.fitnessapp.domain.models.workouts.Muscle
import com.zhdanon.fitnessapp.domain.usecases.exercises.AddExerciseUseCase
import com.zhdanon.fitnessapp.domain.usecases.exercises.UploadExerciseVideoUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.File

class AddExerciseViewModel(
    private val addExerciseUseCase: AddExerciseUseCase,
    private val uploadExerciseVideoUseCase: UploadExerciseVideoUseCase
) : ViewModel() {

    var name by mutableStateOf("")
    var selectedMuscles by mutableStateOf<List<Muscle>>(emptyList())
    var technique by mutableStateOf("")
    var videoUrl by mutableStateOf<String?>(null)
    var error by mutableStateOf<String?>(null)

    var selectedVideoFile: File? = null
    var selectedVideoUri by mutableStateOf<Uri?>(null)
    var selectedVideoName by mutableStateOf<String?>(null)

    private val _events = MutableSharedFlow<AddExerciseEvent>()
    val events = _events.asSharedFlow()

    sealed interface AddExerciseEvent {
        data object Saved : AddExerciseEvent
    }

    fun onVideoSelected(uri: Uri, file: File, name: String) {
        selectedVideoUri = uri
        selectedVideoFile = file
        selectedVideoName = name
    }

    fun save() {
        viewModelScope.launch {
            try {
                // 1. Создаём упражнение
                val created = addExerciseUseCase(
                    ExerciseRequest(
                        name = name,
                        muscleGroups = selectedMuscles.map { it.title },
                        technique = technique,
                        videoUrl = null
                    )
                )

                // 2. Загружаем видео, если выбрано
                selectedVideoFile?.let { file ->
                    val url = uploadExerciseVideoUseCase(created.id, file)
                    videoUrl = url
                }

                // 3. Сообщаем UI
                _events.emit(AddExerciseEvent.Saved)

            } catch (e: Exception) {
                error = e.message
            }
        }
    }
}