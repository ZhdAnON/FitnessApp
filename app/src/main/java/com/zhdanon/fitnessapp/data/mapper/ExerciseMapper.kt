package com.zhdanon.fitnessapp.data.mapper

import com.zhdanon.fitnessapp.data.dto.workouts.ExerciseDto
import com.zhdanon.fitnessapp.data.dto.workouts.ExerciseRequest
import com.zhdanon.fitnessapp.domain.models.workouts.Exercise

fun ExerciseDto.toDomain() = Exercise(
    id = id,
    name = name,
    muscleGroups = muscleGroups,
    technique = technique,
    videoUrl = videoUrl
)

fun Exercise.toRequest() = ExerciseRequest(
    name = name,
    muscleGroups = muscleGroups,
    technique = technique,
    videoUrl = videoUrl
)