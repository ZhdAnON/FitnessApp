package com.zhdanon.fitnessapp.presentation.admin

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.zhdanon.fitnessapp.presentation.auth.AuthViewModel
import com.zhdanon.fitnessapp.presentation.navigation.AdminInnerNavHost

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminRootScreen(
    authViewModel: AuthViewModel
) {
    val adminNavController = rememberNavController()
    var menuExpanded by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Выход") },
            text = { Text("Вы действительно хотите выйти из аккаунта?") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    authViewModel.logout()
                }) {
                    Text("Выйти")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Режим администратора") },
                actions = {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }

                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(Icons.Default.CalendarMonth, contentDescription = null)
                            },
                            text = { Text("Тренировки") },
                            onClick = {
                                menuExpanded = false
                                adminNavController.navigate("workouts") {
                                    popUpTo("workouts") { inclusive = false }
                                }
                            }
                        )

                        DropdownMenuItem(
                            leadingIcon = { Icon(Icons.Default.FitnessCenter, null) },
                            text = { Text("Упражнения") },
                            onClick = {
                                menuExpanded = false
                                adminNavController.navigate("exercises")
                            }
                        )

                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(Icons.Default.Add, contentDescription = null)
                            },
                            text = { Text("Создать тренировку") },
                            onClick = {
                                menuExpanded = false
                                adminNavController.navigate("addWorkout")
                            }
                        )

                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(Icons.Default.FitnessCenter, contentDescription = null)
                            },
                            text = { Text("Создать упражнение") },
                            onClick = {
                                menuExpanded = false
                                adminNavController.navigate("addExercise")
                            }
                        )

                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(Icons.Default.Group, contentDescription = null)
                            },
                            text = { Text("Пользователи") },
                            onClick = {
                                menuExpanded = false
                                adminNavController.navigate("users")
                            }
                        )

                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null)
                            },
                            text = { Text("Выйти") },
                            onClick = {
                                menuExpanded = false
                                showLogoutDialog = true
                            }
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            AdminInnerNavHost(adminNavController)
        }
    }
}