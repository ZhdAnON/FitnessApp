package com.zhdanon.fitnessapp.data.api

import com.zhdanon.fitnessapp.data.dto.workouts.CreatedWorkoutResponse
import com.zhdanon.fitnessapp.domain.api.WorkoutApi
import com.zhdanon.fitnessapp.data.dto.workouts.WorkoutDto
import com.zhdanon.fitnessapp.data.dto.workouts.WorkoutRequest
import com.zhdanon.fitnessapp.domain.api.ApiConfig
import com.zhdanon.fitnessapp.domain.datastore.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path

class WorkoutApiImpl(
    private val client: HttpClient,
    private val tokenStorage: TokenStorage,
    private val apiConfig: ApiConfig
) : WorkoutApi {

    override suspend fun getAllWorkouts(): List<WorkoutDto> =
        client.get {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("workout")
            }
        }.body()

    override suspend fun getWorkoutDetails(id: String): WorkoutDto =
        client.get {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("workout", id)
            }
        }.body()

    override suspend fun addWorkout(request: WorkoutRequest): CreatedWorkoutResponse {
        val token = tokenStorage.getAccessToken()
        val workout = client.post {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("workout")
            }
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)

            setBody(request)
        }
        return workout.body()
    }


    override suspend fun updateWorkout(id: String, request: WorkoutRequest): WorkoutDto {
        val token = tokenStorage.getAccessToken()

        return client.put {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("workout", id)
            }
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun deleteWorkout(id: String) {
        val token = tokenStorage.getAccessToken()
        client.delete {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("workout", id)
            }
            header(HttpHeaders.Authorization, "Bearer $token")
        }
    }

    override suspend fun toggleFavorite(id: String): WorkoutDto =
        client.post("/workout/$id/favorite").body()
}