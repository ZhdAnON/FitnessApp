package com.zhdanon.fitnessapp.presentation.nutrition

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhdanon.fitnessapp.R
import com.zhdanon.fitnessapp.presentation.background.BackgroundContainer
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionListScreen(
    viewModel: NutritionListViewModel = koinViewModel(),
    isAdmin: Boolean = false,
    adminNavController: NavController? = null,
    onNutritionProgramClick: (String) -> Unit = {}
) {
    val programs = viewModel.programs
    val isLoading = viewModel.isLoading
    val error = viewModel.error

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
        },
        floatingActionButton = {
            if (isAdmin) {
                FloatingActionButton(
                    onClick = { adminNavController?.navigate("nutrition_create") }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add nutrition program")
                }
            }
        }
    ) { padding ->

        BackgroundContainer(backgroundRes = R.drawable.bg_nutrition) {

            when {
                isLoading -> CircularProgressIndicator()
                error != null -> Text("Ошибка: $error", color = Color.White)

                else -> Card(
                    modifier = Modifier
                        .padding(
                            top = if (isAdmin) 0.dp else padding.calculateTopPadding(),
                            start = padding.calculateStartPadding(LayoutDirection.Ltr),
                            end = padding.calculateEndPadding(LayoutDirection.Ltr),
                            bottom = padding.calculateBottomPadding()
                        )
                        .padding(8.dp)
                        .fillMaxSize(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0.9f)
                    )
                ) {
                    LazyColumn(Modifier.padding(8.dp)) {
                        items(programs) { program ->
                            Column(
                                Modifier
                                    .fillMaxWidth()
                                    .clickable { onNutritionProgramClick(program.id) }
                                    .padding(vertical = 8.dp)
                            ) {
                                Text(program.category.title, color = Color.Black)
                            }
                        }
                    }
                }
            }
        }
    }
}