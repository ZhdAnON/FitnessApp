import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.zhdanon.fitnessapp.R
import com.zhdanon.fitnessapp.domain.models.nutrition.NutritionCategory
import com.zhdanon.fitnessapp.presentation.admin.nutrition.CreateNutritionViewModel
import com.zhdanon.fitnessapp.presentation.background.BackgroundContainer
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminCreateNutritionScreen(
    navController: NavController,
    viewModel: CreateNutritionViewModel = koinViewModel()
) {
    BackgroundContainer(backgroundRes = R.drawable.bg_nutrition) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("Создать программу питания") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White
                    )
                )
            }
        ) { padding ->

            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {

                var expanded by remember { mutableStateOf(false) }

                OutlinedTextField(
                    value = viewModel.selectedCategory?.title ?: "",
                    onValueChange = {},
                    label = { Text("Категория") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = { expanded = true }) {
                            Icon(Icons.Default.ArrowDropDown, null)
                        }
                    }
                )

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    NutritionCategory.values().forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat.title) },
                            onClick = {
                                viewModel.selectedCategory = cat
                                expanded = false
                            }
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = viewModel.text,
                    onValueChange = { viewModel.text = it },
                    label = { Text("Текст программы") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    maxLines = Int.MAX_VALUE
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.createProgram() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Сохранить")
                }

                viewModel.error?.let {
                    Spacer(Modifier.height(8.dp))
                    Text(it, color = Color.Red)
                }

                if (viewModel.success) {
                    LaunchedEffect(Unit) {
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}