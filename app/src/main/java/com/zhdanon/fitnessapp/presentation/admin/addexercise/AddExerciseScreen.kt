package com.zhdanon.fitnessapp.presentation.admin.addexercise

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddExerciseScreen(
    onSaved: () -> Unit,
    viewModel: AddExerciseViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            if (event is AddExerciseViewModel.AddExerciseEvent.Saved) {
                onSaved()
            }
        }
    }

    Column(
        Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.name = it },
            label = { Text("Название") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        MuscleSelector(
            selected = viewModel.selectedMuscles,
            onSelectedChange = { viewModel.selectedMuscles = it }
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = viewModel.technique,
            onValueChange = { viewModel.technique = it },
            label = { Text("Техника выполнения") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = viewModel.videoUrl,
            onValueChange = { viewModel.videoUrl = it },
            label = { Text("Видео (необязательно)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = viewModel::save,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Создать упражнение")
        }

        viewModel.error?.let {
            Spacer(Modifier.height(12.dp))
            Text("Ошибка: $it", color = Color.Red)
        }
    }
}