package com.zhdanon.fitnessapp.presentation

import android.app.Activity
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun SecureScreen(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val window = (context as Activity).window

    DisposableEffect(Unit) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        onDispose {
            window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
        }
    }
    content()
}