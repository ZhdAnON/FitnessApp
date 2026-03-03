package com.zhdanon.fitnessapp.di

import androidx.lifecycle.SavedStateHandle
import com.zhdanon.fitnessapp.presentation.user.UserViewModel
import com.zhdanon.fitnessapp.presentation.admin.userslist.AdminUsersViewModel
import com.zhdanon.fitnessapp.presentation.admin.addexercise.AddExerciseViewModel
import com.zhdanon.fitnessapp.presentation.admin.addexercise.EditExerciseViewModel
import com.zhdanon.fitnessapp.presentation.auth.AuthStateViewModel
import com.zhdanon.fitnessapp.presentation.auth.AuthViewModel
import com.zhdanon.fitnessapp.presentation.admin.exercises.ExerciseListViewModel
import com.zhdanon.fitnessapp.presentation.admin.nutrition.CreateNutritionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import com.zhdanon.fitnessapp.presentation.auth.LoginViewModel
import com.zhdanon.fitnessapp.presentation.nutrition.NutritionDetailViewModel
import com.zhdanon.fitnessapp.presentation.nutrition.NutritionListViewModel
import com.zhdanon.fitnessapp.presentation.workouts.exerciseDetail.ExerciseDetailViewModel
import com.zhdanon.fitnessapp.presentation.workouts.workoutDetail.WorkoutDetailViewModel
import com.zhdanon.fitnessapp.presentation.admin.calender.AdminCalenderViewModel
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
        UserViewModel(
            getAllWorkoutsUseCase = get(),
            changeOwnPasswordUseCase = get()
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
            deleteUserUseCase = get(),
            getSavedTokenUseCase = get()
        )
    }
    viewModel {
        AdminCalenderViewModel(
            getAllWorkoutsUseCase = get(),
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
        _root_ide_package_.com.zhdanon.fitnessapp.presentation.admin.workoutsEditor.AddWorkoutViewModel(
            addWorkoutUseCase = get(),
            getExercisesUseCase = get()
        )
    }
    viewModel {
        _root_ide_package_.com.zhdanon.fitnessapp.presentation.admin.workoutsEditor.EditWorkoutViewModel(
            getWorkoutUseCase = get(),
            updateWorkoutUseCase = get(),
            getExercisesUseCase = get()
        )
    }
    viewModel { ExerciseListViewModel(getExerciseUseCase = get()) }
    viewModel { AddExerciseViewModel(addExerciseUseCase = get()) }
    viewModel { (handle: SavedStateHandle) ->
        ExerciseDetailViewModel(
            exercisesUseCase = get(),
            savedStateHandle = handle
        )
    }
    viewModel { (exerciseId: String) ->
        EditExerciseViewModel(
            getExerciseUseCase = get(),
            updateExerciseUseCase = get(),
            savedStateHandle = SavedStateHandle(mapOf("exerciseId" to exerciseId))
        )
    }

    // Nutrition
    viewModel { CreateNutritionViewModel(createUseCase = get()) }
    viewModel { NutritionDetailViewModel(getDetailsUseCase = get()) }
    viewModel { NutritionListViewModel(getAllUseCase = get()) }
}