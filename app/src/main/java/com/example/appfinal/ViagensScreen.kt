package com.example.appfinal

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appfinal.viewModel.RegisterNewTravelFactory
import com.example.appfinal.viewModel.RegisterNewTravelModel
import com.example.appfinal.viewModel.RegisterNewUserViewModel
import com.example.appfinal.viewModel.RegisterNewUserViewModelFactory

//
//fun TelaCadastroUsuario(onBackNavigate: () -> Unit){
//    val keyboardController = LocalSoftwareKeyboardController.current
//    val application = LocalContext.current.applicationContext as Application
//    val viewModel: RegisterNewUserViewModel = viewModel(
//        factory = RegisterNewUserViewModelFactory(application)
//    )
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TelaViagens(onBackNavigate: () -> Unit, userID: String) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: RegisterNewTravelModel = viewModel(
        factory = RegisterNewTravelFactory(application)
    )
    println("ID do usuário: $userID <-------------------------------------------------------------------------------")

    var destination by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }
    Scaffold(topBar =
    { TopAppBar(title = { Text(text = "Nova Viagem! :)") }, navigationIcon = { }) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = viewModel.destino,
                onValueChange = { viewModel.destino = it },
                label = { Text("Destino") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            )
            OutlinedTextField(
                value = viewModel.datainicio,
                onValueChange = { viewModel.datainicio = it },
                label = { Text("Data Inicio") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            )
            OutlinedTextField(
                value = viewModel.dataFim,
                onValueChange = { viewModel.dataFim = it },
                label = { Text("Data Final") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            )
            OutlinedTextField(
                value = viewModel.orcamento.toString(),
                onValueChange = { viewModel.orcamento = it.toFloatOrNull() ?: 0f },
                label = { Text("Orçamento") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { /* Ação para avançar para o próximo campo */ }
                ),
                modifier = Modifier.padding(16.dp)
            ).toString()

            Button(
                onClick = {
                    viewModel.register(0)
                    println()
                    onBackNavigate()
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "OK"
                )
            }
        }
    }
}