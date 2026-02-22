package com.zhdanon.fitnessapp.domain.api

import com.zhdanon.fitnessapp.data.dto.workouts.ExerciseRequest
import com.zhdanon.fitnessapp.domain.models.workouts.Exercise

interface ExerciseApi {
    suspend fun getExercises(): List<Exercise>
    suspend fun getExerciseById(id: String): Exercise
    suspend fun createExercise(exercise: ExerciseRequest): Exercise
}