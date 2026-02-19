package com.zhdanon.fitnessapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.zhdanon.fitnessapp.data.dto.workouts.CreatedWorkoutResponse
import com.zhdanon.fitnessapp.data.dto.workouts.WorkoutRequest
import com.zhdanon.fitnessapp.domain.api.WorkoutApi
import com.zhdanon.fitnessapp.data.mapper.toDomain
import com.zhdanon.fitnessapp.data.mapper.toRequest
import com.zhdanon.fitnessapp.domain.models.workouts.Workout
import com.zhdanon.fitnessapp.domain.repositories.WorkoutRepository

class WorkoutRepositoryImpl(
    private val workoutApi: WorkoutApi
) : WorkoutRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getAllWorkouts(): List<Workout> =
        workoutApi.getAllWorkouts().map { it.toDomain() }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWorkoutDetails(id: String): Workout =
        workoutApi.getWorkoutDetails(id).toDomain()

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun addWorkout(workout: WorkoutRequest): CreatedWorkoutResponse {
        return workoutApi.addWorkout(workout)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun updateWorkout(id: String, workout: WorkoutRequest): Workout {
        return workoutApi.updateWorkout(id, workout).toDomain()
    }

    override suspend fun deleteWorkout(id: String) {
        workoutApi.deleteWorkout(id)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun toggleFavorite(workoutId: String): Workout =
        workoutApi.toggleFavorite(workoutId).toDomain()
}