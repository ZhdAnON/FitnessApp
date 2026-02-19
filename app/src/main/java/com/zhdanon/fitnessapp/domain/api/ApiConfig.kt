package com.zhdanon.fitnessapp.domain.api

import android.content.Context
import java.util.Properties

class ApiConfig(context: Context) {
    private val props = Properties().apply {
        val stream = context.assets.open("config.properties")
        load(stream)
    }

    val HOST = props.getProperty("api_host")
    val PORT = props.getProperty("api_port").toInt()
}