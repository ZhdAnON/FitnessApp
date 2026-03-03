package com.zhdanon.fitnessapp.presentation.nutrition

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.zhdanon.fitnessapp.R
import com.zhdanon.fitnessapp.presentation.background.BackgroundContainer
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionDetailScreen(
    programId: String,
    viewModel: NutritionDetailViewModel = koinViewModel(),
    isAdmin: Boolean = false
) {
    val program = viewModel.program
    val isLoading = viewModel.isLoading
    val error = viewModel.error

    LaunchedEffect(programId) {
        viewModel.loadProgram(programId)
    }

    BackgroundContainer(backgroundRes = R.drawable.bg_nutrition) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                if (!isAdmin) {
                    TopAppBar(
                        title = { Text(program?.category?.title ?: "Программа") },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            titleContentColor = Color.White
                        )
                    )
                }
            }
        ) { padding ->
            when {
                isLoading -> CircularProgressIndicator()
                error != null -> Text("Ошибка: $error", color = Color.White)

                program != null -> Card(
                    modifier = Modifier
                        .padding(
                            top = if (isAdmin) 0.dp else padding.calculateTopPadding(),
                            start = padding.calculateStartPadding(LayoutDirection.Ltr),
                            end = padding.calculateEndPadding(LayoutDirection.Ltr)
                        )
                        .padding(8.dp)
                        .fillMaxSize(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.9f)
                    )
                ) {
                    Column(
                        Modifier
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        if (isAdmin) {
                            // Заголовок выбранной программы
                            Text(
                                text = program.category.title,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.Black
                            )
                            Spacer(Modifier.height(12.dp))
                        }

                        Text(program.text, color = Color.Black)
                    }
                }
            }
        }
    }
}