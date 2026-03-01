package com.zhdanon.fitnessapp.presentation.admin.workoutsEditor.mapper

import com.zhdanon.fitnessapp.data.dto.workouts.RepsRequest
import com.zhdanon.fitnessapp.data.dto.workouts.RoundsRequest
import com.zhdanon.fitnessapp.data.dto.workouts.SetExerciseRequest
import com.zhdanon.fitnessapp.data.dto.workouts.WorkoutRequest
import com.zhdanon.fitnessapp.data.dto.workouts.WorkoutSetRequest
import com.zhdanon.fitnessapp.presentation.admin.workoutsEditor.draft.RepsDraft
import com.zhdanon.fitnessapp.presentation.admin.workoutsEditor.draft.RoundsDraft
import com.zhdanon.fitnessapp.presentation.admin.workoutsEditor.draft.WorkoutExerciseDraft
import com.zhdanon.fitnessapp.presentation.admin.workoutsEditor.draft.WorkoutSetDraft
import com.zhdanon.fitnessapp.presentation.admin.workoutsEditor.draft.WorkoutUiState

fun RoundsDraft.toRequest(): RoundsRequest = when (this) {
    is RoundsDraft.Fixed -> RoundsRequest(
        type = "Fixed",
        count = count.toIntOrNull()
    )
    is RoundsDraft.Range -> RoundsRequest(
        type = "Range",
        from = from.toIntOrNull(),
        to = to.toIntOrNull()
    )
    is RoundsDraft.TimeFixed -> RoundsRequest(
        type = "TimeFixed",
        duration = duration.toIntOrNull()
    )
    is RoundsDraft.TimeRange -> RoundsRequest(
        type = "TimeRange",
        from = from.toIntOrNull(),
        to = to.toIntOrNull()
    )
}

fun RepsDraft.toRequest(): RepsRequest = when (this) {
    is RepsDraft.Fixed -> RepsRequest(
        type = "Fixed",
        count = count.toIntOrNull()
    )
    is RepsDraft.Range -> RepsRequest(
        type = "Range",
        from = from.toIntOrNull(),
        to = to.toIntOrNull()
    )
    is RepsDraft.TimeFixed -> RepsRequest(
        type = "TimeFixed",
        duration = duration.toIntOrNull()
    )
    is RepsDraft.TimeRange -> RepsRequest(
        type = "TimeRange",
        from = from.toIntOrNull(),
        to = to.toIntOrNull()
    )
    is RepsDraft.None -> RepsRequest(type = "None")
}

fun WorkoutExerciseDraft.toRequest() =
    SetExerciseRequest(
        exerciseId = exerciseId,
        reps = reps.toRequest(),
        note = note.ifBlank { null }
    )

fun WorkoutSetDraft.toRequest() =
    WorkoutSetRequest(
        order = order,
        protocol = protocol,
        rounds = rounds.toRequest(),
        exercises = exercises.map { it.toRequest() },
        note = note.ifBlank { null }
    )

fun WorkoutUiState.toRequest() =
    WorkoutRequest(
        date = date.toString(),
        title = title,
        sets = sets.map { it.toRequest() }
    )