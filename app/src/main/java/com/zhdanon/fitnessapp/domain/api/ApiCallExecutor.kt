package com.zhdanon.fitnessapp.domain.api

import com.zhdanon.fitnessapp.domain.repositories.AuthRepository
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode

class ApiCallExecutor(
    private val authRepository: AuthRepository
) {
    suspend fun <T> execute(block: suspend () -> T): T? {
        return try {
            block()
        } catch (e: ClientRequestException) {
            if (e.response.status == HttpStatusCode.Unauthorized) {
                if (authRepository.refreshToken()) {
                    block()
                } else {
                    null
                }
            } else {
                throw e
            }
        }
    }
}