package com.zhdanon.fitnessapp.data.api

import com.zhdanon.fitnessapp.domain.api.UserApi
import com.zhdanon.fitnessapp.data.dto.auth.AddUserRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.UpdatePasswordRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.UpdateRoleRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.UserDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody

class UserApiImpl(
    private val client: HttpClient
) : UserApi {

    override suspend fun getAllUsers(): List<UserDto> =
        client.get("/admin/users").body()

    override suspend fun addUser(request: AddUserRequestDto): UserDto =
        client.post("/admin/users") {
            setBody(request)
        }.body()

    override suspend fun updateUserRole(userId: String, request: UpdateRoleRequestDto) {
        client.put("/admin/users/$userId/role") {
            setBody(request)
        }
    }

    override suspend fun updateUserPassword(userId: String, request: UpdatePasswordRequestDto) {
        client.put("/admin/users/$userId/password") {
            setBody(request)
        }
    }

    override suspend fun deleteUser(userId: String) {
        client.delete("/admin/users/$userId")
    }
}