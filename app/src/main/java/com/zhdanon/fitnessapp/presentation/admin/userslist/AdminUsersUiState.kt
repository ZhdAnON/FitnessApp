package com.zhdanon.fitnessapp.presentation.admin.userslist

import com.zhdanon.fitnessapp.domain.models.auth.User

data class AdminUsersUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentUserId: String? = null
)