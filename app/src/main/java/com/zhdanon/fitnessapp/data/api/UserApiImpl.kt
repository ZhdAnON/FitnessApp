package com.zhdanon.fitnessapp.data.api

import com.zhdanon.fitnessapp.domain.api.UserApi
import com.zhdanon.fitnessapp.data.dto.auth.AddUserRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.UpdatePasswordRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.UpdateRoleRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.UserDto
import com.zhdanon.fitnessapp.domain.api.ApiConfig
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

class UserApiImpl(
    private val client: HttpClient,
    private val apiConfig: ApiConfig
) : UserApi {

    override suspend fun getAllUsers(): List<UserDto> =
        client.get {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("admin", "users")
            }
        }.body()

    override suspend fun addUser(request: AddUserRequestDto): UserDto =
        client.post {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("admin", "users")
            }
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

    override suspend fun updateUserRole(userId: String, request: UpdateRoleRequestDto) {
        client.put {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("admin", "users", userId, "role")
            }
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    override suspend fun updateUserPassword(userId: String, request: UpdatePasswordRequestDto) {
        client.put {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("admin", "users", userId, "password")
            }
            contentType(ContentType.Application.Json)
            setBody(request)
        }
    }

    override suspend fun deleteUser(userId: String) {
        client.delete {
            url {
                protocol = URLProtocol.HTTP
                host = apiConfig.HOST
                port = apiConfig.PORT
                path("admin", "users", userId)
            }
        }
    }
}