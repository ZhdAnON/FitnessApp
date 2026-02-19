package com.zhdanon.fitnessapp.data.network

import com.zhdanon.fitnessapp.data.dto.auth.AuthResponseDto
import com.zhdanon.fitnessapp.domain.datastore.TokenStorage
import com.zhdanon.fitnessapp.domain.models.auth.AuthToken
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.api.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

class AuthInterceptor(
    private val tokenStorage: TokenStorage,
    private val refreshClient: HttpClient
) {

    val plugin = createClientPlugin("AuthInterceptor") {

        onResponse { response ->
            if (response.status == HttpStatusCode.Unauthorized) {

                val newToken = refreshToken()

                if (newToken != null) {
                    tokenStorage.saveToken(newToken)
                } else {
                    tokenStorage.clear()
                }
            }
        }
    }

    private suspend fun refreshToken(): AuthToken? {
        val old = tokenStorage.tokenFlow.firstOrNull() ?: return null

        return try {
            val response = refreshClient.post("http://62.84.116.160:8080/auth/refresh") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("refreshToken" to old.refreshToken))
            }.body<AuthResponseDto>()

            AuthToken(
                accessToken = response.accessToken,
                refreshToken = response.refreshToken
            )
        } catch (e: Exception) {
            null
        }
    }
}