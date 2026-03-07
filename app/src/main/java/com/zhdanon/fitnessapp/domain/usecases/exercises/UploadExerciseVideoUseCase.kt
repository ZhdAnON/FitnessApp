package com.zhdanon.fitnessapp.domain.usecases.exercises

import com.zhdanon.fitnessapp.domain.repositories.ExerciseRepository
import java.io.File

class UploadExerciseVideoUseCase(
    private val repo: ExerciseRepository
) {
    suspend operator fun invoke(id: String, file: File): String {
        return repo.uploadVideo(id, file)
    }
}