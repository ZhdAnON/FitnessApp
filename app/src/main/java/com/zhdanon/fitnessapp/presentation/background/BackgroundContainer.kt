package com.zhdanon.fitnessapp.presentation.background

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun BackgroundContainer(
    @DrawableRes backgroundRes: Int,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(backgroundRes),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // затемнение — НУЖНО отключить клики
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.05f))
                .pointerInput(Unit) {} // ← блокирует pointerInput, но НЕ клики
        )

        // контент — кликабельный
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {} // ← гарантирует, что клики доходят
        ) {
            content()
        }
    }
}