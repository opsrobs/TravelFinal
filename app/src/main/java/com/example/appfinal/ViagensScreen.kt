package com.example.appfinal

import android.annotation.SuppressLint
import android.app.Application
import android.widget.DatePicker
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
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
    val focusManager = LocalFocusManager.current
    var showDialog by remember { mutableStateOf(false) }
    var showDate by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    var selectedOption by remember { mutableStateOf(0) }


    val context = LocalContext.current

    val ano: Int
    val mes: Int
    val dia: Int
    val calendario = Calendar.getInstance()

    ano = calendario.get(Calendar.YEAR)
    mes = calendario.get(Calendar.MONTH)
    dia = calendario.get(Calendar.DAY_OF_MONTH)

    calendario.time = Date()

    val mDatePickerDialogStart = android.app.DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            viewModel.datainicio = "$mDayOfMonth/${mMonth + 1}/$mYear"
            focusManager.clearFocus()
        }, ano, mes, dia

    )
    val mDatePickerDialogEnd = android.app.DatePickerDialog(
        context,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            viewModel.dataFim = "$mDayOfMonth/${mMonth + 1}/$mYear"
            focusManager.clearFocus()
        }, ano, mes, dia
    )

    fun openDatePicker(focused: Boolean, date: String) {
        if (focused && date == "Data Inicial") {
            mDatePickerDialogStart.show();
        }
        if (focused && date == "Data Final") {
            mDatePickerDialogEnd.show()
        }
    }
    Scaffold(topBar =
    { TopAppBar(title = { Text(text = "Nova Viagem! :)") }, navigationIcon = { }) }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .clickable { focusManager.clearFocus() },
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
            }
            OutlinedTextField(
                value = viewModel.datainicio,
                onValueChange = {},
                label = { Text("Data Inicio") },
                modifier = Modifier.onFocusChanged { a ->
                    openDatePicker(
                        a.isFocused,
                        "Data Inicial"
                    )
                }
            )
            OutlinedTextField(
                value = viewModel.dataFim,
                onValueChange = { viewModel.dataFim = it },
                label = { Text("Data Final") },
                modifier = Modifier.onFocusChanged { b ->
                    openDatePicker(
                        b.isFocused,
                        "Data Final"
                    )
                }
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
                    selected = viewModel.reason  ==1,
                    onClick = { viewModel.reason = 1 },
                )
                Text("Lazer", Modifier.padding(start = 8.dp))

                Spacer(modifier = Modifier.width(16.dp))
                RadioButton(
                    selected = viewModel.reason  == 0,
                    onClick = { viewModel.reason = 0 },
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

fun checkFields(viewModel: RegisterNewTravelModel): Boolean {
    return viewModel.orcamento != null &&
            viewModel.destino?.isNotEmpty() == true &&
            viewModel.datainicio?.isNotEmpty() == true &&
            viewModel.dataFim?.isNotEmpty() == true
}

