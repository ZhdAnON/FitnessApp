package com.zhdanon.fitnessapp.domain.datastore

import com.zhdanon.fitnessapp.domain.models.auth.AuthToken
import kotlinx.coroutines.flow.Flow

interface TokenStorage {
    val tokenFlow: Flow<AuthToken?>

    suspend fun getToken(): AuthToken?
    suspend fun getAccessToken(): String?
    suspend fun saveToken(token: AuthToken)
    suspend fun saveAccessToken(access: String)
    suspend fun saveRefreshToken(refresh: String)
    suspend fun clear()
}