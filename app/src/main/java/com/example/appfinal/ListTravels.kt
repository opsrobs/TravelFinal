package com.example.appfinal


import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appfinal.HomeScreen
import com.example.appfinal.entity.Travel
import com.example.appfinal.viewModel.RegisterNewTravelModel
import com.example.appfinal.viewModel.RegisterNewTravelViewModelFactory
import com.example.appfinal.viewModel.RegisterNewUserViewModel
import com.example.appfinal.viewModel.RegisterNewUserViewModelFactory

@Composable
fun screen(
    travels: Travel,
    iconReason: Int,
    isItemSelected: Boolean,
    viewModel: RegisterNewTravelModel,
    onCardClick: (Travel) -> Unit,
    onNavigateHome: () -> Unit,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCardClick(travels) },
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
                        text = travels.destino,
                        style = MaterialTheme.typography.subtitle1
                    )
                    Text(
                        text = "${travels.dataInicio} --> ${travels.dataFinal}",
                        style = MaterialTheme.typography.subtitle2
                    )
                    Text(
                        text = "Valor Total: ${formatFloat(travels.orcamento)}R$",
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }

        }

    }
    if (isItemSelected) {
        moreExpenses(travels, viewModel = viewModel)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    viewModel.updateExpenses(travels.id, calculateExpense(travels.orcamento,viewModel.orcamento))
                    onNavigateHome()
                },
                modifier = Modifier
                    .padding(top = 16.dp)
                    .width(150.dp)
            ) {
                Text("Atualizar")
            }
        }
    }

}

fun formatFloat(number: Float): String {
    return String.format("%.2f", number)
}

// Uso:
fun calculateExpense(oldExpense: Float, newExpense: Float): Float {
    if (newExpense != 0.0f) {
        return oldExpense + newExpense
    } else {
        return oldExpense
    }
}


@Composable
fun moreExpenses(travels: Travel, viewModel: RegisterNewTravelModel) {
    var orcamento = travels.orcamento
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = orcamento.toString(),
            onValueChange = { orcamento = it.toFloatOrNull() ?: 0f },
            label = { Text("Despesa atual") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {

                }
            ),
            modifier = Modifier.padding(16.dp),
            enabled = false
        )
        OutlinedTextField(
            value = viewModel.orcamento.toString(),
            onValueChange = { viewModel.orcamento = it.toFloatOrNull() ?: 0f },
            label = { Text("Nova Despesa") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {

                }
            ),
            modifier = Modifier.padding(16.dp)
        ).toString()
    }

}

@Composable
fun ListTravels(userID: String, onNavigateHome:() -> Unit) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: RegisterNewTravelModel = viewModel(
        factory = RegisterNewTravelViewModelFactory(application)
    )

    val selectedTravelId = remember { mutableStateOf<Int?>(null) }

    viewModel.getTravels(Integer.parseInt(userID))
    val travels by viewModel.travels.collectAsState()


    LazyColumn() {
        items(items = travels.filter { selectedTravelId.value == null || it.id == selectedTravelId.value }) { travel ->
            val iconReason = when (travel.reason) {
                0 -> R.drawable.bussines
                else -> R.drawable.lazer
            }
            screen(travel, iconReason, selectedTravelId.value != null, viewModel, onCardClick = {
                selectedTravelId.value = it.id
            },
                onNavigateHome = { selectedTravelId.value = null })
        }
    }
}
