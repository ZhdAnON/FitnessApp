package com.zhdanon.fitnessapp.data.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object TokenPreferences {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
}