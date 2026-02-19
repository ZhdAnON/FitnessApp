package com.zhdanon.fitnessapp.data.repository

import com.zhdanon.fitnessapp.data.dto.workouts.ExerciseRequest
import com.zhdanon.fitnessapp.domain.api.ExerciseApi
import com.zhdanon.fitnessapp.domain.models.workouts.Exercise
import com.zhdanon.fitnessapp.domain.repositories.ExerciseRepository

class ExerciseRepositoryImpl(
    private val exerciseApi: ExerciseApi
): ExerciseRepository {

    override suspend fun getExercises(): List<Exercise> {
        return exerciseApi.getExercises()
    }

    override suspend fun createExercise(exercise: ExerciseRequest): Exercise {
        return exerciseApi.createExercise(exercise)
    }
}