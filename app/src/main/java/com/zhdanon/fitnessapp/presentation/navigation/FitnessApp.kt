package com.zhdanon.fitnessapp.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zhdanon.fitnessapp.domain.models.auth.UserRole
import com.zhdanon.fitnessapp.presentation.admin.AdminRootScreen
import com.zhdanon.fitnessapp.presentation.auth.AuthStateViewModel
import com.zhdanon.fitnessapp.presentation.auth.AuthViewModel
import com.zhdanon.fitnessapp.presentation.auth.LoginScreen
import com.zhdanon.fitnessapp.presentation.user.UserRootScreen
import com.zhdanon.fitnessapp.presentation.workouts.workoutDetail.WorkoutDetailRoute
import org.koin.compose.viewmodel.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FitnessApp(authStateViewModel: AuthStateViewModel) {
    val startDestination = authStateViewModel.startDestination
    if (startDestination == null) {
        LoadingScreen()
        return
    }
    FitnessNavHost(startDestination = startDestination)
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FitnessNavHost(startDestination: String) {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = koinViewModel()

    // Logout listener
    LaunchedEffect(Unit) {
        authViewModel.isLoggedOut.collect {
            navController.navigate("login") {
                popUpTo(0)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // ---------------- LOGIN ----------------
        composable("login") {
            LoginScreen(
                onLoginSuccess = { user ->
                    navController.navigate(
                        when (user.role) {
                            UserRole.ADMIN -> "admin"
                            UserRole.USER -> "user"
                        }
                    ) {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // ---------------- USER ROOT ----------------
        composable("user") {
            UserRootScreen(
                authViewModel = authViewModel,
                onWorkoutClick = { id ->
                    navController.navigate("workout/$id")
                }
            )
        }

        // ---------------- WORKOUT DETAIL ----------------
        composable(
            route = "workout/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")!!
            val isAdmin = startDestination == "admin"

            WorkoutDetailRoute(
                workoutId = id,
                onEdit = { id ->
                    navController.navigate("editWorkout/$id")
                },
                isAdmin = isAdmin
            )
        }

        // ---------------- ADMIN ROOT ----------------
        composable("admin") {
            AdminRootScreen(
                authViewModel = authViewModel
            )
        }
    }
}