package com.zhdanon.fitnessapp.presentation.workouts.editor.mapper

import com.zhdanon.fitnessapp.domain.models.workouts.Reps
import com.zhdanon.fitnessapp.domain.models.workouts.Rounds
import com.zhdanon.fitnessapp.domain.models.workouts.Workout
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.WorkoutUiState
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.RepsDraft
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.RoundsDraft
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.WorkoutExerciseDraft
import com.zhdanon.fitnessapp.presentation.workouts.editor.draft.WorkoutSetDraft

fun Workout.toDraftState() = WorkoutUiState(
    id = id,
    date = date,
    title = title,
    sets = sets.sortedBy { it.order }.map { set ->
        WorkoutSetDraft(
            localId = System.nanoTime(),
            order = set.order,
            protocol = set.protocol,
            rounds = set.rounds.toDraft(),
            exercises = set.exercises.map { ex ->
                WorkoutExerciseDraft(
                    localId = System.nanoTime(),
                    exerciseId = ex.exerciseId,
                    reps = ex.reps.toDraft(),
                    note = ex.note.orEmpty()
                )
            },
            note = set.note.orEmpty()
        )
    }
)

fun Rounds.toDraft(): RoundsDraft = when (this) {
    is Rounds.Fixed -> RoundsDraft.Fixed(count)
    is Rounds.Range -> RoundsDraft.Range(from, to)
    is Rounds.TimeFixed -> RoundsDraft.TimeFixed(duration)
    is Rounds.TimeRange -> RoundsDraft.TimeRange(from, to)
}

fun Reps.toDraft(): RepsDraft = when (this) {
    is Reps.Fixed -> RepsDraft.Fixed(count)
    is Reps.Range -> RepsDraft.Range(from, to)
    is Reps.TimeFixed -> RepsDraft.TimeFixed(duration)
    is Reps.TimeRange -> RepsDraft.TimeRange(from, to)
    Reps.None -> RepsDraft.None
}