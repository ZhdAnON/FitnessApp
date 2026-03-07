package com.zhdanon.fitnessapp.data.api

import com.zhdanon.fitnessapp.data.dto.workouts.ExerciseDto
import com.zhdanon.fitnessapp.data.dto.workouts.ExerciseRequest
import com.zhdanon.fitnessapp.data.mapper.toDomain
import com.zhdanon.fitnessapp.domain.api.ApiConfig
import com.zhdanon.fitnessapp.domain.api.ExerciseApi
import com.zhdanon.fitnessapp.domain.models.workouts.Exercise
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path

class ExerciseApiImpl(
    private val client: HttpClient,
    private val apiConfig: ApiConfig
) : ExerciseApi {

    override suspend fun getExercises(): List<Exercise> {
        return client.get {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("/exercises")
            }
        }.body<List<ExerciseDto>>()
            .map { it.toDomain() }
    }

    override suspend fun getExerciseById(id: String): Exercise {
        return client.get {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("/exercises", id)
            }
        }.body<ExerciseDto>().toDomain()
    }

    override suspend fun createExercise(exercise: ExerciseRequest): Exercise {
        return client.post {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("/exercises")
            }
            contentType(ContentType.Application.Json)
            setBody(exercise)
        }.body<ExerciseDto>().toDomain()
    }

    override suspend fun updateExercise(id: String, request: ExerciseRequest): Exercise {
        val dto = client.put {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("/exercises", id)
            }
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body<ExerciseDto>()

        return dto.toDomain()
    }

    override suspend fun deleteExercise(id: String) {
        client.delete {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("exercises", id)
            }
        }
    }
}