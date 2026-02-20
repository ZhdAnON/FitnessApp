package com.zhdanon.fitnessapp

import android.os.Build
import com.zhdanon.fitnessapp.presentation.navigation.FitnessApp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import com.zhdanon.fitnessapp.presentation.auth.AuthStateViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authStateViewModel: AuthStateViewModel = getViewModel()

        enableEdgeToEdge()
        setContent {
            FitnessApp(authStateViewModel)
        }
    }
}