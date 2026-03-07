package com.zhdanon.fitnessapp.data.dto.workouts

import kotlinx.serialization.Serializable

@Serializable
data class VideoUploadResponse(
    val videoUrl: String
)