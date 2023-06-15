package com.example.appfinal

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appfinal.viewModel.RegisterNewUserViewModel
import com.example.appfinal.viewModel.RegisterNewUserViewModelFactory
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(
    onNavigateHome: (id: Int) -> Unit,
    onNavigateCadastroUsuario: () -> Unit,
    onNavigateListarViagens: () -> Unit,
    onNavigateCadastroViagem: (id: Int) -> Unit
) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: RegisterNewUserViewModel = viewModel(
        factory = RegisterNewUserViewModelFactory(application)
    )
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.viagpng),
            contentDescription = "",
            modifier = Modifier.size(280.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.name = it },
            label = { Text("Usuário") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
        OutlinedTextField(
            value = viewModel.passoword,
            onValueChange = { viewModel.passoword = it },
            label = { Text("Senha") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            visualTransformation = PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val isValidUser = viewModel.verifyIfUserExists()
                if (isValidUser) {
                    onNavigateHome.invoke(viewModel.userTravel(viewModel.name))
                } else {
                    println("erro <<<<<<<<<<<<<<<<<<<<<<<<<<<<")
                }
            },
            modifier = Modifier
                .width(280.dp)
                .height(60.dp),

            colors = ButtonDefaults.buttonColors(backgroundColor = Color("#a2c9c6".toColorInt())),
                    contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = "Login")
        }
        TextButton(
            onClick = { onNavigateCadastroUsuario() },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.textButtonColors(backgroundColor = Color.Transparent)
        ) {
            Text(text = "Criar novo usuário")
        }
    }

}