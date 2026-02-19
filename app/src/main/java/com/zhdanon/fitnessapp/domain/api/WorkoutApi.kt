package com.zhdanon.fitnessapp.domain.api

import com.zhdanon.fitnessapp.data.dto.workouts.CreatedWorkoutResponse
import com.zhdanon.fitnessapp.data.dto.workouts.WorkoutDto
import com.zhdanon.fitnessapp.data.dto.workouts.WorkoutRequest

interface WorkoutApi {
    suspend fun getAllWorkouts(): List<WorkoutDto>
    suspend fun getWorkoutDetails(id: String): WorkoutDto
    suspend fun addWorkout(request: WorkoutRequest): CreatedWorkoutResponse
    suspend fun updateWorkout(id: String, request: WorkoutRequest): WorkoutDto
    suspend fun deleteWorkout(id: String)
    suspend fun toggleFavorite(id: String): WorkoutDto
}