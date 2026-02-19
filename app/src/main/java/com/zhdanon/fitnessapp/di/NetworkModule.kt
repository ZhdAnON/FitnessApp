package com.zhdanon.fitnessapp.di

import com.zhdanon.fitnessapp.domain.api.AuthApi
import com.zhdanon.fitnessapp.domain.api.WorkoutApi
import com.zhdanon.fitnessapp.domain.api.UserApi
import com.zhdanon.fitnessapp.data.api.AuthApiImpl
import com.zhdanon.fitnessapp.data.api.ExerciseApiImpl
import com.zhdanon.fitnessapp.data.repository.AuthRepositoryImpl
import com.zhdanon.fitnessapp.data.api.WorkoutApiImpl
import com.zhdanon.fitnessapp.data.api.UserApiImpl
import com.zhdanon.fitnessapp.data.datastore.TokenStorageImpl
import com.zhdanon.fitnessapp.data.network.AuthInterceptor
import com.zhdanon.fitnessapp.data.repository.ExerciseRepositoryImpl
import com.zhdanon.fitnessapp.data.repository.WorkoutRepositoryImpl
import com.zhdanon.fitnessapp.data.repository.UserRepositoryImpl
import com.zhdanon.fitnessapp.domain.api.ExerciseApi
import com.zhdanon.fitnessapp.domain.datastore.TokenStorage
import com.zhdanon.fitnessapp.domain.repositories.AuthRepository
import com.zhdanon.fitnessapp.domain.repositories.WorkoutRepository
import com.zhdanon.fitnessapp.domain.repositories.UserRepository
import com.zhdanon.fitnessapp.domain.api.ApiConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {

    single { ApiConfig(get()) }

    single<TokenStorage> { TokenStorageImpl(get()) }

    // Client without Interceptor — for refresh
    single(named("refreshClient")) {
        HttpClient(Android) {
            install(ContentNegotiation) { json() }
        }
    }

    // Interceptor
    single {
        AuthInterceptor(
            tokenStorage = get(),
            refreshClient = get(named("refreshClient"))
        )
    }

    // Main client — with Interceptor
    single(named("mainClient")) {
        HttpClient(Android) {
            install(ContentNegotiation) { json() }
        }
    }

    // API
    single<AuthApi> {
        AuthApiImpl(
            client = get(named("mainClient")),
            tokenStorage = get(),
            apiConfig = get()
        )
    }
    single<UserApi> {
        UserApiImpl(get(named("mainClient")))
    }
    single<WorkoutApi> {
        WorkoutApiImpl(
            client = get(named("mainClient")),
            tokenStorage = get(),
            apiConfig = get()
        )
    }
    single<ExerciseApi> {
        ExerciseApiImpl(
            client = get(named("mainClient")),
            apiConfig = get()
        )
    }


    // Repositories
    single<AuthRepository> {
        AuthRepositoryImpl(
            get(),
            get(),
            get(named("refreshClient"))
        )
    }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<WorkoutRepository> { WorkoutRepositoryImpl(get()) }
    single<ExerciseRepositoryImpl> { ExerciseRepositoryImpl(get()) }
}