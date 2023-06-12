package com.example.appfinal

import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appfinal.viewModel.RegisterNewUserViewModel
import com.example.appfinal.viewModel.RegisterNewUserViewModelFactory

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TelaCadastroUsuario(onBackNavigate: () -> Unit){
    val keyboardController = LocalSoftwareKeyboardController.current
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
        OutlinedTextField( //campo usuário
            value = viewModel.name,
            onValueChange = { viewModel.name = it },
            label = { Text("Usuário") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
        OutlinedTextField( //campo senha
            value = viewModel.passoword,
            onValueChange = { viewModel.passoword = it },
            label = { Text("Senha") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            visualTransformation = PasswordVisualTransformation(),
        )
        OutlinedTextField( //campo e-mail
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            label = { Text("Endereço de e-mail") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button( //botão cadastrar
            onClick = {
                viewModel.registrar()
                onBackNavigate()
            },
            modifier = Modifier
                //.fillMaxWidth()
                .width(280.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cyan),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = "Cadastrar")
        }
    }
}