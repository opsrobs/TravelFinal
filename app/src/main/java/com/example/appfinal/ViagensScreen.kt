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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appfinal.viewModel.*
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable()
fun TelaViagens(onNavigateHome: () -> Unit, userID: String) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: RegisterNewTravelModel = viewModel(
        factory = RegisterNewTravelFactory(application)
    )
    val expenseModel: RegisterNewExpenseModel = viewModel(
        factory = RegisterNewExpenseFactory(application)
    )

    val scope = rememberCoroutineScope()

    var selectedOption by remember { mutableStateOf(0) }
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
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly, // espaço igual entre os elementos
                modifier = Modifier.fillMaxWidth() // preenche a largura máxima disponível
            ) {
                dateButtons(viewModel)
            }
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
                value = expenseModel.valueExpense.toString(),
                onValueChange = { expenseModel.valueExpense = it.toFloatOrNull() ?: 0f },
                label = { Text("Orçamento") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { /* Ação para avançar para o próximo campo */ }
                ),
                modifier = Modifier.padding(16.dp)
            ).toString()
            Row {
                RadioButton(
                    selected = selectedOption == 0,
                    onClick = { selectedOption = 0 },
                )
                Text("Lazer", Modifier.padding(start = 8.dp))

                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(
                    selected = selectedOption == 1,
                    onClick = { selectedOption = 1 },
                )
                Text("Negócios", Modifier.padding(start = 8.dp))

            }

            Button(
                onClick = {
                    var id = 0

                    scope.launch {
                        if (checkFields(viewModel)) {
                            viewModel.register(Integer.parseInt(userID))
                            expenseModel.register(viewModel.getTravelByName(viewModel.destino))
                            println("F!")
                            onNavigateHome()
                        }

                    }
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(150.dp)
            ) {
                Text(
                    text = "OK"
                )
            }
        }
    }
}

@Composable
fun dateButtons(viewModel: RegisterNewTravelModel) {
    var showDialogStart by remember { mutableStateOf(false) }
    var showDialogEnd by remember { mutableStateOf(false) }
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    var startDate by remember { mutableStateOf(Date()) }
    var endDate by remember { mutableStateOf(Date()) }
    Column {
        Button(
            onClick = { showDialogStart = true },
            modifier = Modifier
                .height(40.dp)
        ) {
            Text("Data de entrada")
        }

        DatePickerDialog(showDialogStart, { showDialogStart = false }) { newDate ->
            startDate = newDate
            showDialogStart = false
        }
        viewModel.datainicio = dateFormatter.format(startDate)
    }

    Column {
        Button(
            onClick = { showDialogEnd = true },
            modifier = Modifier
                .height(40.dp)
        ) {
            Text("Data de Saida")
        }

        DatePickerDialog(showDialogEnd, { showDialogEnd = false }) { newDate ->
            endDate = newDate
            showDialogEnd = false
        }
        viewModel.dataFim = dateFormatter.format(endDate)
        print(dateFormatter.format(endDate))
    }
}

fun checkFields(viewModel: RegisterNewTravelModel): Boolean {
    return viewModel.orcamento != null &&
            viewModel.destino?.isNotEmpty() == true &&
            viewModel.datainicio?.isNotEmpty() == true &&
            viewModel.dataFim?.isNotEmpty() == true
}


@Composable
fun DatePickerDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onDateSet: (Date) -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            android.app.DatePickerDialog(LocalContext.current, { _, year, month, dayOfMonth ->
                onDateSet(GregorianCalendar(year, month, dayOfMonth).time)
            }, 2023, 6, 12).show()
        }
    }
}
