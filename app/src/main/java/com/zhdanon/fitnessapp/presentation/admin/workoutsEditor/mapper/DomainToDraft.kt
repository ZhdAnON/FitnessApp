package com.zhdanon.fitnessapp.presentation.admin.workoutsEditor.mapper

import com.zhdanon.fitnessapp.domain.models.workouts.Reps
import com.zhdanon.fitnessapp.domain.models.workouts.Rounds
import com.zhdanon.fitnessapp.domain.models.workouts.Workout
import com.zhdanon.fitnessapp.presentation.admin.workoutsEditor.draft.RepsDraft
import com.zhdanon.fitnessapp.presentation.admin.workoutsEditor.draft.RoundsDraft
import com.zhdanon.fitnessapp.presentation.admin.workoutsEditor.draft.WorkoutExerciseDraft
import com.zhdanon.fitnessapp.presentation.admin.workoutsEditor.draft.WorkoutSetDraft
import com.zhdanon.fitnessapp.presentation.admin.workoutsEditor.draft.WorkoutUiState

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
    is Rounds.Fixed -> RoundsDraft.Fixed(
        count = count.toString()
    )
    is Rounds.Range -> RoundsDraft.Range(
        from = from.toString(),
        to = to.toString()
    )
    is Rounds.TimeFixed -> RoundsDraft.TimeFixed(
        duration = duration.toString()
    )
    is Rounds.TimeRange -> RoundsDraft.TimeRange(
        from = from.toString(),
        to = to.toString()
    )
}

fun Reps.toDraft(): RepsDraft = when (this) {
    is Reps.Fixed -> RepsDraft.Fixed(
        count = count.toString()
    )
    is Reps.Range -> RepsDraft.Range(
        from = from.toString(),
        to = to.toString()
    )
    is Reps.TimeFixed -> RepsDraft.TimeFixed(
        duration = duration.toString()
    )
    is Reps.TimeRange -> RepsDraft.TimeRange(
        from = from.toString(),
        to = to.toString()
    )
    Reps.None -> RepsDraft.None
}