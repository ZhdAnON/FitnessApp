package com.zhdanon.fitnessapp.data.api

import com.zhdanon.fitnessapp.data.dto.workouts.ExerciseDto
import com.zhdanon.fitnessapp.data.dto.workouts.ExerciseRequest
import com.zhdanon.fitnessapp.data.mapper.toDomain
import com.zhdanon.fitnessapp.domain.api.ApiConfig
import com.zhdanon.fitnessapp.domain.api.ExerciseApi
import com.zhdanon.fitnessapp.domain.models.workouts.Exercise
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
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
            .map { dto ->
                Exercise(
                    id = dto.id,
                    name = dto.name,
                    muscleGroups = dto.muscleGroups,
                    technique = dto.technique,
                    videoUrl = dto.videoUrl
                )
            }
    }

    override suspend fun getExerciseById(id: String): Exercise {
        val dto = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("/exercises", id)
            }
        }.body<ExerciseDto>()
        return dto.toDomain()
    }


    override suspend fun createExercise(exercise: ExerciseRequest): Exercise {
        val dto = client.post {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("/exercises")
            }
            contentType(ContentType.Application.Json)
            setBody(exercise)
        }.body<ExerciseDto>()

        return Exercise(
            id = dto.id,
            name = dto.name,
            muscleGroups = dto.muscleGroups,
            technique = dto.technique,
            videoUrl = dto.videoUrl
        )
    }
}