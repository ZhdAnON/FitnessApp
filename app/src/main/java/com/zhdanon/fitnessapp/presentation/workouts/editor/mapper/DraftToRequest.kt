package com.zhdanon.fitnessapp.presentation.workouts.editor.mapper

import com.zhdanon.fitnessapp.data.dto.workouts.RepsRequest
import com.zhdanon.fitnessapp.data.dto.workouts.RoundsRequest
import com.zhdanon.fitnessapp.data.dto.workouts.SetExerciseRequest
import com.zhdanon.fitnessapp.data.dto.workouts.WorkoutRequest
import com.zhdanon.fitnessapp.data.dto.workouts.WorkoutSetRequest
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.WorkoutUiState
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.RepsDraft
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.RoundsDraft
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.WorkoutExerciseDraft
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.WorkoutSetDraft

fun RoundsDraft.toRequest(): RoundsRequest = when (this) {
    is RoundsDraft.Fixed -> RoundsRequest("Fixed", count = count)
    is RoundsDraft.Range -> RoundsRequest("Range", from = from, to = to)
    is RoundsDraft.TimeFixed -> RoundsRequest("TimeFixed", duration = duration)
    is RoundsDraft.TimeRange -> RoundsRequest("TimeRange", from = from, to = to)
}

fun RepsDraft.toRequest(): RepsRequest = when (this) {
    is RepsDraft.Fixed -> RepsRequest("Fixed", count = count)
    is RepsDraft.Range -> RepsRequest("Range", from = from, to = to)
    is RepsDraft.TimeFixed -> RepsRequest("TimeFixed", duration = duration)
    is RepsDraft.TimeRange -> RepsRequest("TimeRange", from = from, to = to)
    is RepsDraft.None -> RepsRequest("None")
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