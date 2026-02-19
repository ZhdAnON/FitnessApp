package com.zhdanon.fitnessapp.data.repository

import com.zhdanon.fitnessapp.domain.datastore.TokenStorage
import com.zhdanon.fitnessapp.data.dto.auth.AuthResponseDto
import com.zhdanon.fitnessapp.data.dto.auth.ChangePasswordRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.LoginRequestDto
import com.zhdanon.fitnessapp.data.dto.auth.RegisterRequestDto
import com.zhdanon.fitnessapp.data.mapper.toDomain
import com.zhdanon.fitnessapp.domain.api.AuthApi
import com.zhdanon.fitnessapp.domain.models.auth.AuthToken
import com.zhdanon.fitnessapp.domain.models.auth.User
import com.zhdanon.fitnessapp.domain.repositories.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.firstOrNull
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*


class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val tokenStorage: TokenStorage,
    private val refreshClient: HttpClient
) : AuthRepository {

    private var currentUser: User? = null

    override suspend fun register(email: String, password: String): Pair<User, AuthToken> {
        val response = authApi.register(RegisterRequestDto(email, password))
        val user = response.user.toDomain()
        val token = AuthToken(response.accessToken, response.refreshToken)
        tokenStorage.saveToken(token)
        currentUser = user
        return user to token
    }

    override suspend fun login(email: String, password: String): Pair<User, AuthToken> {
        val response = authApi.login(LoginRequestDto(email, password))
        val user = response.user.toDomain()
        val token = AuthToken(response.accessToken, response.refreshToken)
        tokenStorage.saveToken(token)
        currentUser = user
        return user to token
    }

    override suspend fun changeOwnPassword(oldPassword: String, newPassword: String) {
        authApi.changeOwnPassword(ChangePasswordRequestDto(oldPassword, newPassword))
    }

    override suspend fun logout() {
        tokenStorage.clear()
        currentUser = null
    }

    override fun getCurrentUser(): User? = currentUser

    override suspend fun getCurrentToken(): AuthToken? =
        tokenStorage.tokenFlow.firstOrNull()

    override suspend fun fetchProfile(): User {
        val dto = safeApiCall { authApi.getProfile() } ?: throw Exception("Unauthorized")
        val user = dto.toDomain()
        currentUser = user
        return user
    }

    suspend fun <T> safeApiCall(block: suspend () -> T): T? {
        return try {
            block()
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.Unauthorized) {
                if (refreshToken()) {
                    block() // повторяем запрос вручную
                } else {
                    tokenStorage.clear()
                    null
                }
            } else null
        }
    }

    private suspend fun refreshToken(): Boolean {
        val old = tokenStorage.tokenFlow.firstOrNull() ?: return false

        return try {
            val dto = refreshClient.post("http://62.84.116.160:8080/auth/refresh") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("refreshToken" to old.refreshToken))
            }.body<AuthResponseDto>()

            tokenStorage.saveToken(AuthToken(dto.accessToken, dto.refreshToken))
            true
        } catch (e: Exception) {
            false
        }
    }
}