package com.zhdanon.fitnessapp.presentation.nutrition

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhdanon.fitnessapp.R
import com.zhdanon.fitnessapp.presentation.background.BackgroundContainer
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionListScreen(
    navController: NavController,
    viewModel: NutritionListViewModel = koinViewModel(),
    isAdmin: Boolean = false
) {
    val programs = viewModel.programs
    val isLoading = viewModel.isLoading
    val error = viewModel.error

    BackgroundContainer(backgroundRes = R.drawable.bg_nutrition) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                if (!isAdmin) {
                    TopAppBar(
                        title = { Text("Программы питания") },
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

                else -> Card(
                    modifier = Modifier
//                        .padding(padding)
                        .padding(8.dp)
                        .fillMaxSize(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.9f)
                    )
                ) {
                    LaunchedEffect(programs) {
                        println("NutritionListScreen: programs size = ${programs.size}")
                    }

                    LazyColumn(Modifier.padding(8.dp)) {
                        items(programs) { program ->
                            println("ITEM COMPOSED: ${program.id}")
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .background(Color.Transparent)
                                    .clickable {
                                        println("CLICK! programId = ${program.id}")
                                        println("NutritionListScreen: programId = ${program.id}")
                                        val route = if (isAdmin) {
                                            "nutrition/${program.id}"   // внутри admin navhost
                                        } else {
                                            "nutrition/${program.id}"   // внутри global navhost
                                        }

                                        navController.navigate(route)
                                    }
                                    .padding(vertical = 8.dp)
                            ) {
                                if (isAdmin) {
                                    Text(
                                        text = "Программы питания",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Black
                                    )
                                    Spacer(Modifier.height(8.dp))
                                }
                                Text(program.category.title, color = Color.Black)
                            }
                        }
                    }
                }
            }
        }
    }
}