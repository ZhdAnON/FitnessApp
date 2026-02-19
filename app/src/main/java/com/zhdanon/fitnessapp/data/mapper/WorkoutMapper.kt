package com.zhdanon.fitnessapp.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.zhdanon.fitnessapp.data.dto.workouts.WorkoutDto
import com.zhdanon.fitnessapp.data.dto.workouts.WorkoutRequest
import com.zhdanon.fitnessapp.domain.models.workouts.Workout
import java.time.LocalDate
import kotlin.collections.map

@RequiresApi(Build.VERSION_CODES.O)
fun WorkoutDto.toDomain() = Workout(
    id = id,
    date = LocalDate.parse(date),
    title = title,
    sets = sets.map { it.toDomain() }
)

fun Workout.toRequest() = WorkoutRequest(
    date = date.toString(),
    title = title,
    sets = sets.map { it.toRequest() }
)