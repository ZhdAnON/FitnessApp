package com.zhdanon.fitnessapp.presentation.workouts.workoutDetail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zhdanon.fitnessapp.domain.models.workouts.Exercise
import com.zhdanon.fitnessapp.domain.models.workouts.WorkoutExercise
import com.zhdanon.fitnessapp.presentation.workouts.editor.mapper.toPrettyString

@Composable
fun WorkoutExerciseRow(
    index: Int,
    exercise: WorkoutExercise,
    baseExercise: Exercise?,
    onExerciseClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onExerciseClick(exercise.exerciseId) }
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "$index. ${baseExercise?.name ?: "Упражнение ${exercise.exerciseId}"}",
            style = MaterialTheme.typography.bodyLarge
        )

        Text(
            text = "Повторы: ${exercise.reps.toPrettyString()}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.DarkGray
        )

        exercise.note?.takeIf { it.isNotBlank() }?.let {
            Text(
                text = "Примечание: $it",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}