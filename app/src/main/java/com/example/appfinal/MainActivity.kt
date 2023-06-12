package com.example.appfinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appfinal.ui.theme.AppFinalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppFinalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyApp()
                    //Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(onNavigateHome = {
                navController.navigate("home")
            },
                onNavigateCadastroUsuario = {
                    navController.navigate("cadastroUsuario")
                },
                onNavigateCadastroViagem = { id ->
                    navController.navigate("cadastroViagem/$id")
                }
            )
        }

        composable("home") {
            HomeScreen(/*onNavigateHome = {
                navController.navigate("home")
            }*/
            )
        }
        composable("cadastroUsuario") {
            TelaCadastroUsuario {
                navController.navigateUp()
            }
        }

        composable("cadastroViagem/{userID}",
            arguments = listOf(navArgument("userID") { type = NavType.StringType })
        ) {
            val id = it.arguments?.getString("userID")
            if (id != null) {
                TelaViagens(
                    onBackNavigate = { navController.navigateUp() }, id
                )
            }
        }

    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppFinalTheme {
        Greeting("Android")
    }
}