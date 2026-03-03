package com.zhdanon.fitnessapp.di

import com.zhdanon.fitnessapp.domain.api.AuthApi
import com.zhdanon.fitnessapp.domain.api.WorkoutApi
import com.zhdanon.fitnessapp.domain.api.UserApi
import com.zhdanon.fitnessapp.data.api.AuthApiImpl
import com.zhdanon.fitnessapp.data.api.ExerciseApiImpl
import com.zhdanon.fitnessapp.data.api.NutritionApiImpl
import com.zhdanon.fitnessapp.data.repository.AuthRepositoryImpl
import com.zhdanon.fitnessapp.data.api.WorkoutApiImpl
import com.zhdanon.fitnessapp.data.api.UserApiImpl
import com.zhdanon.fitnessapp.data.datastore.TokenStorageImpl
import com.zhdanon.fitnessapp.data.repository.ExerciseRepositoryImpl
import com.zhdanon.fitnessapp.data.repository.NutritionRepositoryImpl
import com.zhdanon.fitnessapp.data.repository.WorkoutRepositoryImpl
import com.zhdanon.fitnessapp.data.repository.UserRepositoryImpl
import com.zhdanon.fitnessapp.domain.api.ApiCallExecutor
import com.zhdanon.fitnessapp.domain.api.ExerciseApi
import com.zhdanon.fitnessapp.domain.datastore.TokenStorage
import com.zhdanon.fitnessapp.domain.repositories.AuthRepository
import com.zhdanon.fitnessapp.domain.repositories.WorkoutRepository
import com.zhdanon.fitnessapp.domain.repositories.UserRepository
import com.zhdanon.fitnessapp.domain.api.ApiConfig
import com.zhdanon.fitnessapp.domain.api.NutritionApi
import com.zhdanon.fitnessapp.domain.repositories.ExerciseRepository
import com.zhdanon.fitnessapp.domain.repositories.NutritionRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {

    single { ApiConfig(get()) }

    single<TokenStorage> { TokenStorageImpl(get()) }

    single { ApiCallExecutor(get()) }

    // Клиент для refresh — без токена
    single(named("refreshClient")) {
        HttpClient(Android) {
            expectSuccess = true
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        encodeDefaults = true
                    }
                )
            }
        }
    }

    // Главный клиент — ВСЕГДА добавляет Authorization
    single(named("mainClient")) {
        val tokenStorage: TokenStorage = get()

        HttpClient(Android) {
            expectSuccess = true
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    encodeDefaults = true
                })
            }

            install(DefaultRequest) {
                val token = runBlocking { tokenStorage.getAccessToken() }
                if (!token.isNullOrBlank()) {
                    header(HttpHeaders.Authorization, "Bearer $token")
                }
            }
        }
    }

    // API
    single<AuthApi> {
        AuthApiImpl(
            client = get(named("mainClient")),
            apiConfig = get()
        )
    }

    single<UserApi> {
        UserApiImpl(
            client = get(named("mainClient")),
            apiConfig = get()
        )
    }

    single<WorkoutApi> {
        WorkoutApiImpl(
            client = get(named("mainClient")),
            apiConfig = get()
        )
    }

    single<ExerciseApi> {
        ExerciseApiImpl(
            client = get(named("mainClient")),
            apiConfig = get()
        )
    }

    single<NutritionApi> {
        NutritionApiImpl(
            client = get(named("mainClient")),
            apiConfig = get()
        )
    }

    // Repositories
    single<AuthRepository> {
        AuthRepositoryImpl(
            authApi = get(),
            tokenStorage = get()
        )
    }
    single<UserRepository> {
        UserRepositoryImpl(
            userApi = get(),
            executor = get()
        )
    }
    single<WorkoutRepository> {
        WorkoutRepositoryImpl(
            workoutApi = get(),
            executor = get()
        )
    }
    single<ExerciseRepository> {
        ExerciseRepositoryImpl(
            exerciseApi = get(),
            executor = get()
        )
    }
    single<NutritionRepository> {
        NutritionRepositoryImpl(
            api = get(),
            executor = get()
        )
    }
}