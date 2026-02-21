package com.zhdanon.fitnessapp.domain.util

import android.util.Base64
import org.json.JSONObject

fun extractUserIdFromJwt(token: String): String? {
    return try {
        val parts = token.split(".")
        if (parts.size != 3) return null

        val payloadJson = String(Base64.decode(parts[1], Base64.URL_SAFE))
        val json = JSONObject(payloadJson)
        json.getString("userId")
    } catch (e: Exception) {
        null
    }
}