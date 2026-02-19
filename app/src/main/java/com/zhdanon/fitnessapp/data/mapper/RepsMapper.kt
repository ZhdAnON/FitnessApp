package com.zhdanon.fitnessapp.data.mapper

import RepsDto
import com.zhdanon.fitnessapp.data.dto.workouts.RepsRequest
import com.zhdanon.fitnessapp.domain.models.workouts.Reps

fun RepsDto.toDomain(): Reps = when (this) {
    is RepsDto.Fixed -> Reps.Fixed(count)
    is RepsDto.Range -> Reps.Range(from, to)
    is RepsDto.TimeFixed -> Reps.TimeFixed(duration)
    is RepsDto.TimeRange -> Reps.TimeRange(from, to)
    RepsDto.None -> Reps.None
}

fun Reps.toRequest(): RepsRequest = when (this) {
    is Reps.Fixed -> RepsRequest(
        type = "Fixed",
        count = count
    )
    is Reps.Range -> RepsRequest(
        type = "Range",
        from = from,
        to = to
    )
    is Reps.TimeFixed -> RepsRequest(
        type = "TimeFixed",
        duration = duration
    )
    is Reps.TimeRange -> RepsRequest(
        type = "TimeRange",
        from = from,
        to = to
    )
    is Reps.None -> RepsRequest(
        type = "None"
    )
}