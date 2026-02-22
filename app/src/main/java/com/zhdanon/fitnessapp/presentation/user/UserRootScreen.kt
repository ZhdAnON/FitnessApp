package com.zhdanon.fitnessapp.presentation.user

import ChangePasswordDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zhdanon.fitnessapp.presentation.auth.AuthViewModel
import com.zhdanon.fitnessapp.presentation.workouts.workoutsList.components.WorkoutCalendar
import com.zhdanon.fitnessapp.presentation.workouts.workoutsList.components.WorkoutsList
import org.koin.compose.viewmodel.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserRootScreen(
    viewModel: UserViewModel = koinViewModel(),
    authViewModel: AuthViewModel,
    onWorkoutClick: (String) -> Unit = {}
) {
    val state = viewModel.uiState

    var menuExpanded by remember { mutableStateOf(false) }
    var showPasswordDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Тренировки") },
                actions = {
                    Box {
                        IconButton(onClick = { menuExpanded = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Меню")
                        }

                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Сменить пароль") },
                                onClick = {
                                    menuExpanded = false
                                    showPasswordDialog = true
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Выйти") },
                                onClick = {
                                    menuExpanded = false
                                    authViewModel.logout()
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)   // ← ВАЖНО: Scaffold сам даёт правильные отступы
                .fillMaxSize()
        ) {

            // Календарь
            WorkoutCalendar(
                selectedDate = viewModel.selectedDate,
                workoutDates = viewModel.workoutDates,
                onDateSelected = { viewModel.onDateSelected(it) }
            )

            Spacer(Modifier.height(16.dp))

            // Список тренировок выбранной даты
            when {
                state.isLoading -> CircularProgressIndicator()
                state.error != null -> Text("Ошибка: ${state.error}")
                else -> {
                    val workouts = viewModel.workoutsOfSelectedDate

                    if (workouts.isEmpty()) {
                        Text(
                            text = "Выходной",
                            modifier = Modifier.padding(16.dp),
                            color = Color.Gray
                        )
                    } else {
                        WorkoutsList(
                            workouts = workouts,
                            isAdmin = false,
                            onClick = onWorkoutClick,
                            onDelete = { }
                        )
                    }
                }
            }
        }
    }

    if (showPasswordDialog) {
        ChangePasswordDialog(
            onDismiss = { showPasswordDialog = false },
            onSave = { newPassword ->
                viewModel.changeOwnPassword(newPassword)
                showPasswordDialog = false
            }
        )
    }
}