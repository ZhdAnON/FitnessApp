package com.zhdanon.fitnessapp.data.repository

import com.zhdanon.fitnessapp.data.dto.workouts.ExerciseRequest
import com.zhdanon.fitnessapp.domain.api.ApiCallExecutor
import com.zhdanon.fitnessapp.domain.api.ExerciseApi
import com.zhdanon.fitnessapp.domain.models.workouts.Exercise
import com.zhdanon.fitnessapp.domain.repositories.ExerciseRepository

class ExerciseRepositoryImpl(
    private val exerciseApi: ExerciseApi,
    private val executor: ApiCallExecutor
) : ExerciseRepository {

    override suspend fun getExercises(): List<Exercise> = exerciseApi.getExercises()

    override suspend fun getExerciseById(id: String): Exercise {
        return exerciseApi.getExerciseById(id)
    }

    override suspend fun createExercise(exercise: ExerciseRequest): Exercise =
        executor.execute { exerciseApi.createExercise(exercise) }!!

    override suspend fun updateExercise(id: String, request: ExerciseRequest): Exercise =
        executor.execute { exerciseApi.updateExercise(id, request) }!!

    override suspend fun deleteExercise(id: String) {
        executor.execute {
            exerciseApi.deleteExercise(id)
        }
    }
}