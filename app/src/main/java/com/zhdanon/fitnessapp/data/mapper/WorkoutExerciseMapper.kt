package com.zhdanon.fitnessapp.data.mapper

import com.zhdanon.fitnessapp.data.dto.workouts.SetExerciseDto
import com.zhdanon.fitnessapp.data.dto.workouts.SetExerciseRequest
import com.zhdanon.fitnessapp.domain.models.workouts.WorkoutExercise
import com.zhdanon.fitnessapp.domain.util.toReps

fun SetExerciseDto.toDomain() = WorkoutExercise(
    id = id,
    exerciseId = exerciseId,
    reps = reps.toDomain(),
    note = note
)

fun WorkoutExercise.toRequest() = SetExerciseRequest(
    exerciseId = exerciseId,
    reps = reps.toRequest(),
    note = note
)