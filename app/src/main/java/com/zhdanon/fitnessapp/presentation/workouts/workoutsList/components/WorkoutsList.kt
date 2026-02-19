package com.zhdanon.fitnessapp.presentation.workouts.workoutsList.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zhdanon.fitnessapp.domain.models.workouts.Workout

@Composable
fun WorkoutsList(
    workouts: List<Workout>,
    isAdmin: Boolean,
    onClick: (String) -> Unit,
    onDelete: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(workouts) { workout ->
            WorkoutItem(
                workout = workout,
                isAdmin = isAdmin,
                onClick = onClick,
                onDelete = onDelete
            )
        }
    }
}