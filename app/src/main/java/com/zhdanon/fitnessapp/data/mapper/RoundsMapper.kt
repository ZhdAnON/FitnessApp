package com.zhdanon.fitnessapp.data.mapper

import com.zhdanon.fitnessapp.data.dto.workouts.RoundsDto
import com.zhdanon.fitnessapp.data.dto.workouts.RoundsRequest
import com.zhdanon.fitnessapp.domain.models.workouts.Rounds

fun RoundsDto.toDomain(): Rounds = when (type) {
    "Fixed" -> Rounds.Fixed(count ?: 0)
    "Range" -> Rounds.Range(from ?: 0, to ?: 0)
    "TimeFixed" -> Rounds.TimeFixed(duration ?: 0)
    "TimeRange" -> Rounds.TimeRange(from ?: 0, to ?: 0)
    else -> error("Unknown RoundsDto type: $type")
}

fun Rounds.toRequest(): RoundsRequest = when (this) {
    is Rounds.Fixed -> RoundsRequest(
        type = "Fixed",
        count = count
    )
    is Rounds.Range -> RoundsRequest(
        type = "Range",
        from = from,
        to = to
    )
    is Rounds.TimeFixed -> RoundsRequest(
        type = "TimeFixed",
        duration = duration
    )
    is Rounds.TimeRange -> RoundsRequest(
        type = "TimeRange",
        from = from,
        to = to
    )
}