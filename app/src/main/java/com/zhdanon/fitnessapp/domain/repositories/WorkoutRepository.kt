package com.zhdanon.fitnessapp.domain.repositories

import com.zhdanon.fitnessapp.data.dto.workouts.CreatedWorkoutResponse
import com.zhdanon.fitnessapp.data.dto.workouts.WorkoutRequest
import com.zhdanon.fitnessapp.domain.models.workouts.Workout

interface WorkoutRepository {
    suspend fun getAllWorkouts(): List<Workout>
    suspend fun getWorkoutDetails(id: String): Workout
    suspend fun addWorkout(workout: WorkoutRequest): CreatedWorkoutResponse
    suspend fun updateWorkout(id: String, workout: WorkoutRequest): Workout
    suspend fun deleteWorkout(id: String)
    suspend fun toggleFavorite(workoutId: String): Workout
}