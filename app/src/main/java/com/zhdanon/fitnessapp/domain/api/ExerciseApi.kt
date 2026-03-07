package com.zhdanon.fitnessapp.domain.api

import com.zhdanon.fitnessapp.data.dto.workouts.ExerciseRequest
import com.zhdanon.fitnessapp.domain.models.workouts.Exercise
import java.io.File

interface ExerciseApi {
    suspend fun getExercises(): List<Exercise>
    suspend fun getExerciseById(id: String): Exercise
    suspend fun createExercise(exercise: ExerciseRequest): Exercise
    suspend fun updateExercise(id: String, request: ExerciseRequest): Exercise
    suspend fun deleteExercise(id: String)
    suspend fun uploadVideo(id: String, file: File): String
}