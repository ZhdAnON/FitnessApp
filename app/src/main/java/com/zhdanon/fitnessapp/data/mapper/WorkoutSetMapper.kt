package com.zhdanon.fitnessapp.data.mapper

import com.zhdanon.fitnessapp.data.dto.workouts.WorkoutSetDto
import com.zhdanon.fitnessapp.data.dto.workouts.WorkoutSetRequest
import com.zhdanon.fitnessapp.domain.models.workouts.WorkoutSet

fun WorkoutSetDto.toDomain() = WorkoutSet(
    id = id,
    order = order,
    protocol = protocol,
    rounds = rounds.toDomain(),
    exercises = exercises.map { it.toDomain() },
    note = note
)

fun WorkoutSet.toRequest() = WorkoutSetRequest(
    order = order,
    protocol = protocol,
    rounds = rounds.toRequest(),
    exercises = exercises.map { it.toRequest() },
    note = note
)