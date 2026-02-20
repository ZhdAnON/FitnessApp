package com.zhdanon.fitnessapp.di

import com.zhdanon.fitnessapp.presentation.workouts.editor.AddWorkoutViewModel
import com.zhdanon.fitnessapp.presentation.admin.AdminUsersViewModel
import com.zhdanon.fitnessapp.presentation.admin.addexercise.AddExerciseViewModel
import com.zhdanon.fitnessapp.presentation.auth.AuthStateViewModel
import com.zhdanon.fitnessapp.presentation.auth.AuthViewModel
import com.zhdanon.fitnessapp.presentation.workouts.exercises.ExerciseListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import com.zhdanon.fitnessapp.presentation.auth.LoginViewModel
import com.zhdanon.fitnessapp.presentation.workouts.editor.EditWorkoutViewModel
import com.zhdanon.fitnessapp.presentation.workouts.workoutDetail.WorkoutDetailViewModel
import com.zhdanon.fitnessapp.presentation.workouts.workoutsList.WorkoutViewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        LoginViewModel(
            loginUseCase = get()
        )
    }
    viewModel {
        AuthViewModel(
            logoutUseCase = get(),
            autoLoginUseCase = get()
        )
    }
    viewModel {
        AuthStateViewModel(
            getSavedTokenUseCase = get(),
            fetchProfileUseCase = get(),
            autoLoginUseCase = get()
        )
    }
    viewModel {
        AdminUsersViewModel(
            getAllUsersUseCase = get(),
            addUserUseCase = get(),
            updateUserRoleUseCase = get(),
            updateUserPasswordUseCase = get(),
            deleteUserUseCase = get()
        )
    }
    viewModel {
        WorkoutViewModel(
            getAllWorkoutsUseCase = get(),
            toggleFavoriteUseCase = get(),
            deleteWorkoutUseCase = get()
        )
    }
    viewModel {
        WorkoutDetailViewModel(
            getWorkoutUseCase = get(),
            getExercisesUseCase = get()
        )
    }
    viewModel {
        AddWorkoutViewModel(
            addWorkoutUseCase = get(),
            getExercisesUseCase = get()
        )
    }
    viewModel {
        EditWorkoutViewModel(
            getWorkoutUseCase = get(),
            updateWorkoutUseCase = get(),
            getExercisesUseCase = get()
        )
    }
    viewModel { ExerciseListViewModel(getExerciseUseCase = get()) }
    viewModel { AddExerciseViewModel(addExerciseUseCase = get()) }
}