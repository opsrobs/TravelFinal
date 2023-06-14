package com.example.appfinal

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun HomeScreen(userID: String) {

    println("--> $userID")
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(selected = true,
                    onClick = {
                        navController.navigate("cadastroViagem/{userID}")
                    },
                    label = {
                        Text(text = "Novo")
                    },
                    icon = {
                        Icon(Icons.Filled.Home, contentDescription = "")
                    }
                )

                BottomNavigationItem(selected = false,
                    onClick = {
                        navController.navigate("listarViagem")
                    },
                    label = {
                        Text(text = "Viagens")
                    },
                    icon = {
                        Icon(Icons.Filled.AccountBox, contentDescription = "")
                    }
                )

                BottomNavigationItem(selected = false,
                    onClick = {
                        navController.navigate("sobre")
                    },
                    label = {
                        Text(text = "Sobre")
                    },
                    icon = {
                        Icon(Icons.Filled.Add, contentDescription = "")
                    }
                )
            }
        }
    ) {
        Column() {
            NavHost(
                navController = navController,
                startDestination = "listarViagem",
                modifier = Modifier.padding(paddingValues = it)
            ) {
                composable("novo") {
                    TelaNovo()
                }
                composable(
                    "cadastroViagem/{userID}",
                    arguments = listOf(navArgument("userID") { type = NavType.StringType })
                ) {
                    if (userID != null) {
                        Log.i(userID, "$userID Logou")
                        TelaViagens(
                            onNavigateHome = { navController.navigateUp() },
                            userID
                        )
                    }
                }
                composable("sobre") {
                    println("Clicou sobre")
                    TelaSobre()
                }
                composable("listarViagem") {
                    ListTravels()
                }
            }
        }

    }
}