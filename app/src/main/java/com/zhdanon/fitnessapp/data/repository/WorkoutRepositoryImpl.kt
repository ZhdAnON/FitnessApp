package com.zhdanon.fitnessapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.zhdanon.fitnessapp.data.dto.workouts.CreatedWorkoutResponse
import com.zhdanon.fitnessapp.data.dto.workouts.WorkoutRequest
import com.zhdanon.fitnessapp.domain.api.WorkoutApi
import com.zhdanon.fitnessapp.data.mapper.toDomain
import com.zhdanon.fitnessapp.domain.api.ApiCallExecutor
import com.zhdanon.fitnessapp.domain.models.workouts.Workout
import com.zhdanon.fitnessapp.domain.repositories.WorkoutRepository

class WorkoutRepositoryImpl(
    private val workoutApi: WorkoutApi,
    private val executor: ApiCallExecutor
) : WorkoutRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAllWorkouts(): List<Workout> =
        workoutApi.getAllWorkouts().map { it.toDomain() }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWorkoutDetails(id: String): Workout =
        workoutApi.getWorkoutDetails(id).toDomain()

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addWorkout(workout: WorkoutRequest): CreatedWorkoutResponse =
        executor.execute { workoutApi.addWorkout(workout) }!!

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun updateWorkout(id: String, workout: WorkoutRequest): Workout =
        executor.execute { workoutApi.updateWorkout(id, workout) }!!.toDomain()

    override suspend fun deleteWorkout(id: String) {
        executor.execute { workoutApi.deleteWorkout(id) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun toggleFavorite(workoutId: String): Workout =
        executor.execute { workoutApi.toggleFavorite(workoutId) }!!.toDomain()
}