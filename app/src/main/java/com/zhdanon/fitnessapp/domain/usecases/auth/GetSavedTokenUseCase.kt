package com.zhdanon.fitnessapp.domain.usecases.auth

import com.zhdanon.fitnessapp.domain.datastore.TokenStorage
import com.zhdanon.fitnessapp.domain.models.auth.AuthToken
import kotlinx.coroutines.flow.Flow

class GetSavedTokenUseCase(
    private val tokenStorage: TokenStorage
) {
    operator fun invoke(): Flow<AuthToken?> = tokenStorage.tokenFlow
}