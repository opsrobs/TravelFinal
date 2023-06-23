package com.example.appfinal


import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appfinal.HomeScreen
import com.example.appfinal.entity.Expense
import com.example.appfinal.entity.Travel
import com.example.appfinal.entity.TravelWithExpense
import com.example.appfinal.viewModel.*
import kotlinx.coroutines.launch

@Composable
fun screen(
    travels: TravelWithExpense,
    iconReason: Int,
    isItemSelected: Boolean,
    viewModel: RegisterNewTravelModel,
    onCardClick: (TravelWithExpense) -> Unit,
    onNavigateHome: () -> Unit,
) {
    val application = LocalContext.current.applicationContext as Application
    val expenseViewModel: RegisterNewExpenseModel = viewModel(
        factory = RegisterNewExpenseFactory(application)
    )
    val focusManager = LocalFocusManager.current


    var descriptionText by remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCardClick(travels)
                focusManager.clearFocus()
            },
        elevation = 8.dp,
        backgroundColor = Color.White,
    ) {
        Row {
            Image(
                painter = painterResource(iconReason),
                contentDescription = "Icon of travels",
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp)
            )
            Row(modifier = Modifier.padding(4.dp)) {
                Column {
                    Text(
                        text = travels.destino, style = MaterialTheme.typography.subtitle1
                    )
                    Text(
                        text = "${travels.dataInicio} --> ${travels.dataFinal}",
                        style = MaterialTheme.typography.subtitle2
                    )
                    Text(
                        text = "Valor Total: ${formatFloat(travels.valueExpense)}R$",
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    scope.launch {
                        viewModel
                       viewModel.deleteTravel(travels.id)
                        println("deletado")
                    }
                }, modifier = Modifier
                    .padding(top = 16.dp)
                    .width(100.dp)
            ) {
                Text("Exluir")
            }

        }
    }
    if (isItemSelected) {
        moreExpenses(
            descriptionText = descriptionText,
            travels = travels,
            viewModel = viewModel,
            onDescriptionTextChanged = {
                descriptionText = it
            })
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .clickable { focusManager.clearFocus() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    scope.launch {
                        val id = travels.travelID
                        expenseViewModel.registerValueOnExpense(
                            id, descriptionText, viewModel.orcamento
                        )
                        descriptionText = ""
                        viewModel.getTravelsWithExpenses(travels.userID)
                        onNavigateHome()
                    }
                }, modifier = Modifier
                    .padding(top = 16.dp)
                    .width(150.dp)
            ) {
                Text("Atualizar")
            }
        }
    }
}

@Composable
fun ListExpenses(travelId: String) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: RegisterNewExpenseModel = viewModel(
        factory = RegisterNewExpenseFactory(application)
    )
    viewModel.getExpenses(Integer.parseInt(travelId))
    val expenses by viewModel.expenseTravel.collectAsState()
    println(expenses)
    LazyColumn(
        modifier = Modifier
            .height(150.dp)
            .width(1000.dp)
    ) {
        items(items = expenses) {
            expensesPresent(expense = it)
            println(it)

        }
    }
}

@Composable
fun expensesPresent(expense: Expense) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .clickable { focusManager.clearFocus() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Row {
            Text(
                text = expense.descriptionExpense,
                modifier = Modifier
                    .weight(1.0f)
                    .border(1.dp, Color.Black)
                    .padding(4.dp)
            )
            Text(
                text = expense.valueExpense.toString(),
                modifier = Modifier
                    .weight(1.0f)
                    .border(1.dp, Color.Black)
                    .padding(4.dp)
            )
        }
    }
}


fun formatFloat(number: Float): String {
    return String.format("%.2f", number)
}

// Uso:


@Composable
fun moreExpenses(
    descriptionText: String,
    travels: TravelWithExpense,
    viewModel: RegisterNewTravelModel,
    onDescriptionTextChanged: (String) -> Unit
) {
    var orcamento = travels.valueExpense
    val focusManager = LocalFocusManager.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .clickable { focusManager.clearFocus() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ListExpenses(travels.travelID.toString())
        OutlinedTextField(
            value = orcamento.toString(),
            onValueChange = { orcamento = it.toFloatOrNull() ?: 0f },
            label = { Text("Despesa atual") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {

            }),
            modifier = Modifier.padding(16.dp),
            enabled = false
        )
        OutlinedTextField(
            value = viewModel.orcamento.toString(),
            onValueChange = { viewModel.orcamento = it.toFloatOrNull() ?: 0f },
            label = { Text("Nova Despesa") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {

            }),
            modifier = Modifier.padding(6.dp)
        ).toString()
        OutlinedTextField(
            value = descriptionText,
            onValueChange = {
                onDescriptionTextChanged(it)

//                test = it
            },
            label = { Text("Descrição") },
            enabled = true
        )

    }

}

@Composable
fun ListTravels(userID: String, onNavigateHome: () -> Unit) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: RegisterNewTravelModel = viewModel(
        factory = RegisterNewTravelViewModelFactory(application)
    )

    val selectedTravelId = remember { mutableStateOf<Int?>(null) }

    viewModel.getTravelsWithExpenses(Integer.parseInt(userID))
    val travels by viewModel.travelsWithExpense.collectAsState()


    LazyColumn() {
        items(items = travels.filter { selectedTravelId.value == null || it.id == selectedTravelId.value }) { travel ->
            val iconReason = when (travel.reason) {
                0 -> R.drawable.bussines
                else -> R.drawable.lazer
            }
            screen(travel, iconReason, selectedTravelId.value != null, viewModel, onCardClick = {
                selectedTravelId.value = it.id
            }, onNavigateHome = { selectedTravelId.value = null })
        }
    }
}


