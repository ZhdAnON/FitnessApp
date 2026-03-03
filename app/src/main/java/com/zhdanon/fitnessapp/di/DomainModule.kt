package com.zhdanon.fitnessapp.di

import com.zhdanon.fitnessapp.domain.usecases.exercises.AddExerciseUseCase
import com.zhdanon.fitnessapp.domain.usecases.workouts.AddWorkoutUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.AddUserUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.AutoLoginUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.ChangeOwnPasswordUseCase
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
import com.zhdanon.fitnessapp.domain.usecases.workouts.UpdateWorkoutUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.UpdateUserPasswordUseCase
import com.zhdanon.fitnessapp.domain.usecases.auth.UpdateUserRoleUseCase
import com.zhdanon.fitnessapp.domain.usecases.exercises.GetExerciseByIdUseCase
import com.zhdanon.fitnessapp.domain.usecases.exercises.GetExerciseUseCase
import com.zhdanon.fitnessapp.domain.usecases.nutrition.CreateNutritionProgramUseCase
import com.zhdanon.fitnessapp.domain.usecases.nutrition.DeleteNutritionProgramUseCase
import com.zhdanon.fitnessapp.domain.usecases.nutrition.GetAllNutritionProgramsUseCase
import com.zhdanon.fitnessapp.domain.usecases.nutrition.GetNutritionProgramDetailsUseCase
import org.koin.dsl.module

val domainModule = module {
    // Auth
    factory { LoginUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { AutoLoginUseCase(get()) }
    factory { FetchProfileUseCase(get()) }
    factory { GetSavedTokenUseCase(get()) }

    // Users
    factory { AddUserUseCase(get()) }
    factory { DeleteUserUseCase(get()) }
    factory { UpdateUserPasswordUseCase(get()) }
    factory { UpdateUserRoleUseCase(get()) }
    factory { ChangeOwnPasswordUseCase(get()) }
    factory { GetAllUsersUseCase(get()) }
    factory { GetCurrentUserUseCase(get()) }

    // Workouts
    factory { AddWorkoutUseCase(get()) }
    factory { DeleteWorkoutUseCase(get()) }
    factory { GetAllWorkoutsUseCase(get()) }
    factory { GetWorkoutUseCase(get()) }
    factory { UpdateWorkoutUseCase(get()) }

    // Exercises
    factory { AddExerciseUseCase(get()) }
    factory { GetExerciseByIdUseCase(get()) }
    factory { GetExerciseUseCase(get()) }
    factory { GetExercisesUseCase(get()) }

    // Nutrition
    factory { CreateNutritionProgramUseCase(get()) }
    factory { DeleteNutritionProgramUseCase(get()) }
    factory { GetAllNutritionProgramsUseCase(get()) }
    factory { GetNutritionProgramDetailsUseCase(get()) }
}