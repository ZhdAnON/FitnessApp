package com.zhdanon.fitnessapp.presentation.workouts.workoutDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zhdanon.fitnessapp.domain.models.workouts.Exercise
import com.zhdanon.fitnessapp.domain.models.workouts.WorkoutSet
import com.zhdanon.fitnessapp.presentation.workouts.editor.mapper.toPrettyString

@Composable
fun WorkoutSetBlock(
    setNumber: Int,
    set: WorkoutSet,
    exercises: Map<String, Exercise>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF1F8E9), MaterialTheme.shapes.medium)
            .padding(12.dp)
    ) {
        Text(
            text = "Сет $setNumber: ${set.rounds.toPrettyString()}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        set.exercises.forEachIndexed { exIndex, exercise ->
            WorkoutExerciseRow(
                index = exIndex + 1,
                exercise = exercise,
                baseExercise = exercises[exercise.exerciseId]
            )
        }
    }
}