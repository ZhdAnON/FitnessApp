package com.zhdanon.fitnessapp.presentation.admin.calender

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import com.zhdanon.fitnessapp.R
import com.zhdanon.fitnessapp.presentation.background.BackgroundContainer
import com.zhdanon.fitnessapp.presentation.workouts.workoutsList.calender.WorkoutCalendar
import com.zhdanon.fitnessapp.presentation.workouts.workoutsList.WorkoutsList
import org.koin.compose.viewmodel.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdminCalenderScreen(
    navController: NavController,
    workoutViewModel: AdminCalenderViewModel = koinViewModel(),
    onWorkoutClick: (String) -> Unit = {}
) {
    val state = workoutViewModel.uiState

    val savedStateHandle = LocalContext.current
        .let { LocalViewModelStoreOwner.current }
        ?.let { navController.currentBackStackEntry?.savedStateHandle }

    val workoutSaved = savedStateHandle
        ?.getStateFlow("workout_saved", false)
        ?.collectAsState()

    LaunchedEffect(workoutSaved?.value) {
        if (workoutSaved?.value == true) {
            workoutViewModel.loadWorkouts()   // ← перезагружаем список
            savedStateHandle?.set("workout_saved", false)
        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("addWorkout") }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add workout")
            }
        }
    ) { padding ->
        BackgroundContainer(backgroundRes = R.drawable.bg_workouts) {
            Column {
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
                        selectedDate = workoutViewModel.selectedDate,
                        workoutDates = workoutViewModel.workoutDates,
                        onDateSelected = { workoutViewModel.onDateSelected(it) }
                    )
                }

                // Список тренировок выбранной даты
                when {
                    state.isLoading -> CircularProgressIndicator()
                    state.error != null -> Text("Ошибка: ${state.error}")
                    else -> {
                        val workouts = workoutViewModel.workoutsOfSelectedDate

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
                                isAdmin = true,
                                onClick = onWorkoutClick,
                                onDelete = workoutViewModel::deleteWorkout
                            )
                        }
                    }
                }
            }
        }
    }
}