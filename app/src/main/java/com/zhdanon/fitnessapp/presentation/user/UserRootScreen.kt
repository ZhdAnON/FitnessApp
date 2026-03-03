package com.zhdanon.fitnessapp.presentation.user

import ChangePasswordDialog
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhdanon.fitnessapp.R
import com.zhdanon.fitnessapp.presentation.auth.AuthViewModel
import com.zhdanon.fitnessapp.presentation.background.BackgroundContainer
import com.zhdanon.fitnessapp.presentation.workouts.workoutsList.calender.WorkoutCalendar
import com.zhdanon.fitnessapp.presentation.workouts.workoutsList.WorkoutsList
import org.koin.compose.viewmodel.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserRootScreen(
    viewModel: UserViewModel = koinViewModel(),
    authViewModel: AuthViewModel,
    onWorkoutClick: (String) -> Unit = {},
    onNutritionMenuClick: () -> Unit = {}
) {
    val state = viewModel.uiState

    var menuExpanded by remember { mutableStateOf(false) }
    var showPasswordDialog by remember { mutableStateOf(false) }

    BackgroundContainer(backgroundRes = R.drawable.bg_workouts) {
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
                                    text = { Text("Программа питания") },
                                    onClick = {
                                        menuExpanded = false
                                        onNutritionMenuClick()
                                    }
                                )
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
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White,
                        actionIconContentColor = Color.White
                    )
                )
            },
            containerColor = Color.Transparent,
            contentColor = Color.Black,
        ) { padding ->

            Column(
                modifier = Modifier
                    .padding(padding)   // ← ВАЖНО: Scaffold сам даёт правильные отступы
                    .fillMaxSize()
            ) {
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.9f)
                    )
                ) {
                    // Календарь
                    WorkoutCalendar(
                        selectedDate = viewModel.selectedDate,
                        workoutDates = viewModel.workoutDates,
                        onDateSelected = { viewModel.onDateSelected(it) }
                    )
                }

//                Spacer(Modifier.height(8.dp))

                // Список тренировок выбранной даты
                when {
                    state.isLoading -> CircularProgressIndicator()
                    state.error != null -> Text("Ошибка: ${state.error}")
                    else -> {
                        val workouts = viewModel.workoutsOfSelectedDate

                        if (workouts.isEmpty()) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.White.copy(alpha = 0.85f)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = "В этот день выходной",
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    color = Color.Black,
                                    textAlign = TextAlign.Center,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
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