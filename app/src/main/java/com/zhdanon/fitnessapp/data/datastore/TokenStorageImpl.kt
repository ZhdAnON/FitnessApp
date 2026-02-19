package com.zhdanon.fitnessapp.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.zhdanon.fitnessapp.data.datastore.TokenPreferences.ACCESS_TOKEN
import com.zhdanon.fitnessapp.data.datastore.TokenPreferences.REFRESH_TOKEN
import com.zhdanon.fitnessapp.domain.datastore.TokenStorage
import com.zhdanon.fitnessapp.domain.models.auth.AuthToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TokenStorageImpl(
    private val dataStore: DataStore<Preferences>
) : TokenStorage {

    override val tokenFlow: Flow<AuthToken?> =
        dataStore.data.map { prefs ->
            val access = prefs[TokenPreferences.ACCESS_TOKEN]
            val refresh = prefs[TokenPreferences.REFRESH_TOKEN]

            if (access.isNullOrBlank() || refresh.isNullOrBlank()) {
                null
            } else {
                AuthToken(access, refresh)
            }
        }

    override suspend fun getToken(): AuthToken? {
        return dataStore.data.first().let { prefs ->
            val access = prefs[ACCESS_TOKEN]
            val refresh = prefs[REFRESH_TOKEN]

            if (access.isNullOrBlank() || refresh.isNullOrBlank()) {
                null
            } else {
                AuthToken(access, refresh)
            }
        }
    }

    override suspend fun getAccessToken(): String? {
        return dataStore.data.first()[ACCESS_TOKEN]
    }

    override suspend fun saveToken(token: AuthToken) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = token.accessToken
            prefs[REFRESH_TOKEN] = token.refreshToken
        }
    }

    override suspend fun saveAccessToken(access: String) {
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = access
        }
    }

    override suspend fun saveRefreshToken(refresh: String) {
        dataStore.edit { prefs ->
            prefs[REFRESH_TOKEN] = refresh
        }
    }

    override suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}