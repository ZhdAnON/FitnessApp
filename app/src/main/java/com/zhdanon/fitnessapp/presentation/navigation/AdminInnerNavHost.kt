package com.zhdanon.fitnessapp.presentation.navigation

import com.zhdanon.fitnessapp.presentation.workouts.editor.AddWorkoutRoute
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zhdanon.fitnessapp.presentation.admin.AdminCalenderScreen
import com.zhdanon.fitnessapp.presentation.admin.userslist.AdminUsersScreen
import com.zhdanon.fitnessapp.presentation.admin.addexercise.AddExerciseRoute
import com.zhdanon.fitnessapp.presentation.workouts.editor.EditWorkoutRoute
import com.zhdanon.fitnessapp.presentation.workouts.editor.EditWorkoutViewModel
import com.zhdanon.fitnessapp.presentation.admin.exercises.ExerciseListScreen
import com.zhdanon.fitnessapp.presentation.workouts.exerciseDetail.ExerciseDetailScreen
import com.zhdanon.fitnessapp.presentation.workouts.exerciseDetail.ExerciseDetailViewModel
import com.zhdanon.fitnessapp.presentation.workouts.workoutDetail.WorkoutDetailRoute
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AdminInnerNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "workouts"
    ) {
        composable("workouts") {
            AdminCalenderScreen(
                navController = navController,
                onWorkoutClick = { id -> navController.navigate("workout/$id") }
            )
        }

        composable("addWorkout") { backStackEntry ->
            AddWorkoutRoute(
                isEditMode = false,
                onSaved = {
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set("workout_saved", true)
                    navController.popBackStack()
                }
            )
        }

        composable("addExercise") {
            AddExerciseRoute(navController)
        }

        composable("exercises") {
            ExerciseListScreen(
                navController = navController,
                onAddExercise = { navController.navigate("addExercise") }
            )
        }

        composable("exercise/{id}") { backStack ->
            val viewModel: ExerciseDetailViewModel = koinViewModel(
                parameters = { parametersOf(backStack.savedStateHandle) }
            )

            ExerciseDetailScreen(viewModel = viewModel)
        }

        composable("users") {
            AdminUsersScreen()
        }

        composable("workout/{id}") { backStack ->
            val id = backStack.arguments!!.getString("id")!!
            val isAdmin = true
            WorkoutDetailRoute(
                workoutId = id,
                isAdmin = isAdmin,
                onEdit = { navController.navigate("editWorkout/$id") },
                onExerciseClick = { exerciseId ->
                    navController.navigate("exercise/$exerciseId")
                }
            )
        }

        composable("editWorkout/{id}") { backStack ->
            val id = backStack.arguments!!.getString("id")!!

            val viewModel: EditWorkoutViewModel = koinViewModel(
                key = "edit_$id",
                viewModelStoreOwner = backStack
            )

            EditWorkoutRoute(
                workoutId = id,
                viewModel = viewModel,
                onSaved = { navController.navigate("workouts") }
            )
        }
    }
}