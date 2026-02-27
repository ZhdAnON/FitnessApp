package com.zhdanon.fitnessapp.data.api

import CreateNutritionProgramRequest
import NutritionProgramDto
import com.zhdanon.fitnessapp.domain.api.ApiConfig
import com.zhdanon.fitnessapp.domain.api.NutritionApi
import com.zhdanon.fitnessapp.domain.datastore.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path

class NutritionApiImpl(
    private val client: HttpClient,
    private val tokenStorage: TokenStorage,
    private val apiConfig: ApiConfig
) : NutritionApi {

    override suspend fun getAllPrograms(): List<NutritionProgramDto> =
        client.get {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("nutrition")
            }
        }.body()

    override suspend fun getProgramDetails(id: String): NutritionProgramDto =
        client.get {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("nutrition", id)
            }
        }.body()

    override suspend fun createProgram(request: CreateNutritionProgramRequest): NutritionProgramDto {
        val token = tokenStorage.getAccessToken()

        return client.post {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("nutrition")
            }
            header(HttpHeaders.Authorization, "Bearer $token")
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun deleteProgram(id: String) {
        val token = tokenStorage.getAccessToken()

        client.delete {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("nutrition", id)
            }
            header(HttpHeaders.Authorization, "Bearer $token")
        }
    }
}