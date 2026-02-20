package com.zhdanon.fitnessapp.di

import com.zhdanon.fitnessapp.domain.usecases.exercises.AddExerciseUseCase
import com.zhdanon.fitnessapp.domain.usecases.workouts.AddWorkoutUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.AddUserUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.AutoLoginUseCase
import com.zhdanon.fitnessapp.domain.usecases.workouts.DeleteWorkoutUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.DeleteUserUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.FetchProfileUseCase
import com.zhdanon.fitnessapp.domain.usecases.workouts.GetAllWorkoutsUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.GetAllUsersUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.GetCurrentUserUseCase
import com.zhdanon.fitnessapp.domain.usecases.workouts.GetExercisesUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.GetSavedTokenUseCase
import com.zhdanon.fitnessapp.domain.usecases.workouts.GetWorkoutUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.LoginUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.LogoutUseCase
import com.zhdanon.fitnessapp.domain.usecases.workouts.ToggleFavoriteWorkoutUseCase
import com.zhdanon.fitnessapp.domain.usecases.workouts.UpdateWorkoutUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.UpdateUserPasswordUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.UpdateUserRoleUseCase
import com.zhdanon.fitnessapp.domain.usecases.exercises.GetExerciseUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { AddExerciseUseCase(get()) }
    factory { AutoLoginUseCase(get()) }
    factory { GetExerciseUseCase(get()) }
    factory { AddWorkoutUseCase(get()) }
    factory { AddUserUseCase(get()) }
    factory { DeleteWorkoutUseCase(get()) }
    factory { DeleteUserUseCase(get()) }
    factory { FetchProfileUseCase(get()) }
    factory { GetAllWorkoutsUseCase(get()) }
    factory { GetAllUsersUseCase(get()) }
    factory { GetCurrentUserUseCase(get()) }
    factory { GetExercisesUseCase(get()) }
    factory { GetSavedTokenUseCase(get()) }
    factory { GetWorkoutUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { ToggleFavoriteWorkoutUseCase(get()) }
    factory { UpdateWorkoutUseCase(get()) }
    factory { UpdateUserPasswordUseCase(get()) }
    factory { UpdateUserRoleUseCase(get()) }
}