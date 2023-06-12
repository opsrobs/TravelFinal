package com.example.appfinal

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appfinal.viewModel.RegisterNewUserViewModel
import com.example.appfinal.viewModel.RegisterNewUserViewModelFactory
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(
    onNavigateHome: () -> Unit,
    onNavigateCadastroUsuario: () -> Unit,
    onNavigateCadastroViagem: (id: Int) -> Unit
) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: RegisterNewUserViewModel = viewModel(
        factory = RegisterNewUserViewModelFactory(application)
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        //.background(Color.LightGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val ctx = LocalContext.current
        var usuario by remember { mutableStateOf("") }
        var senha by remember { mutableStateOf("") }

        Image( //imagem
            painter = painterResource(id = R.drawable.viagpng),
            contentDescription = "",
            modifier = Modifier.size(280.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            //campo usuário
            value = viewModel.name,
            onValueChange = { viewModel.name = it },
            label = { Text("Usuário") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
        OutlinedTextField(
            //campo senha
            value = viewModel.passoword,
            onValueChange = { viewModel.passoword = it },
            label = { Text("Senha") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            visualTransformation = PasswordVisualTransformation(),
        )
        Spacer(modifier = Modifier.height(20.dp))

        Button( //botão entrar
            onClick = {
                val isValidUser = viewModel.verifyIfUserExists()
                if (isValidUser) {
                    onNavigateCadastroViagem.invoke(viewModel.userTravel(viewModel.name))

                } else {
                    println("erro <<<<<<<<<<<<<<<<<<<<<<<<<<<<")
                }
            },
            modifier = Modifier
                //.fillMaxWidth()
                .width(280.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cyan),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = "Entrar")
        }

        TextButton( //botão cadastrar
            onClick = { onNavigateCadastroUsuario() },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.textButtonColors(backgroundColor = Color.Transparent)
        ) {
            Text(text = "Criar novo usuário")
        }
    }

}