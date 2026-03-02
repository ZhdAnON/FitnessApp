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
    // In-memory кэш — ключевой элемент
    @Volatile
    private var cachedToken: AuthToken? = null

    override val tokenFlow: Flow<AuthToken?> =
        dataStore.data.map { prefs ->
            val access = prefs[ACCESS_TOKEN]
            val refresh = prefs[REFRESH_TOKEN]

            val token = if (access.isNullOrBlank() || refresh.isNullOrBlank()) {
                null
            } else {
                AuthToken(access, refresh)
            }

            // Обновляем кэш при каждом эмите DataStore
            cachedToken = token
            token
        }

    override suspend fun getToken(): AuthToken? {
        // Если токен уже есть в памяти — возвращаем мгновенно
        cachedToken?.let { return it }

        // Иначе читаем из DataStore и кэшируем
        val prefs = dataStore.data.first()
        val access = prefs[ACCESS_TOKEN]
        val refresh = prefs[REFRESH_TOKEN]

        val token = if (access.isNullOrBlank() || refresh.isNullOrBlank()) {
            null
        } else {
            AuthToken(access, refresh)
        }

        cachedToken = token
        return token
    }

    override suspend fun getAccessToken(): String? {
        // Берём из кэша, а не из DataStore
        cachedToken?.accessToken?.let { return it }

        // Если кэш пуст — читаем из DataStore
        val prefs = dataStore.data.first()
        val access = prefs[ACCESS_TOKEN]
        val refresh = prefs[REFRESH_TOKEN]

        val token = if (access.isNullOrBlank() || refresh.isNullOrBlank()) {
            null
        } else {
            AuthToken(access, refresh)
        }

        cachedToken = token
        return token?.accessToken
    }

    override suspend fun saveToken(token: AuthToken) {
        // Сначала обновляем кэш
        cachedToken = token

        // Потом сохраняем в DataStore
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = token.accessToken
            prefs[REFRESH_TOKEN] = token.refreshToken
        }
    }

    override suspend fun saveAccessToken(access: String) {
        // Обновляем кэш
        val current = cachedToken
        cachedToken = if (current != null) {
            current.copy(accessToken = access)
        } else {
            AuthToken(access, "")
        }

        // Сохраняем в DataStore
        dataStore.edit { prefs ->
            prefs[ACCESS_TOKEN] = access
        }
    }

    override suspend fun saveRefreshToken(refresh: String) {
        // Обновляем кэш
        val current = cachedToken
        cachedToken = if (current != null) {
            current.copy(refreshToken = refresh)
        } else {
            AuthToken("", refresh)
        }

        // Сохраняем в DataStore
        dataStore.edit { prefs ->
            prefs[REFRESH_TOKEN] = refresh
        }
    }

    override suspend fun clear() {
        cachedToken = null
        dataStore.edit { it.clear() }
    }
}