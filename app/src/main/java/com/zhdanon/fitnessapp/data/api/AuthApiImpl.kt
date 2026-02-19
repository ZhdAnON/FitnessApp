package com.zhdanon.fitnessapp.data.api

import com.zhdanon.fitnessapp.domain.api.AuthApi
import com.zhdanon.fitnessapp.data.dto.auth.AuthResponseDto
import com.zhdanon.fitnessapp.data.dto.auth.ChangePasswordRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.LoginRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.RegisterRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.UserDto
import com.zhdanon.fitnessapp.domain.api.ApiConfig
import com.zhdanon.fitnessapp.domain.datastore.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import kotlinx.coroutines.flow.firstOrNull

class AuthApiImpl(
    private val client: HttpClient,
    private val tokenStorage: TokenStorage,
    private val apiConfig: ApiConfig
) : AuthApi {

    override suspend fun register(request: RegisterRequestDto): AuthResponseDto =
        client.post {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("auth", "register")
            }
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    override suspend fun login(request: LoginRequestDto): AuthResponseDto =
        client.post {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("auth", "login")
            }
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    override suspend fun changeOwnPassword(request: ChangePasswordRequestDto) {
        client.post {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("auth", "change-password")
            }
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    override suspend fun getProfile(): UserDto {
        val token = tokenStorage.tokenFlow.firstOrNull()
        return client.get {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("user/profile")
            }
            if (token != null) {
                header(HttpHeaders.Authorization, "Bearer ${token.accessToken}")
            }
        }.body()
    }
}