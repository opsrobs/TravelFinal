package com.example.appfinal


import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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

//import com.example.appfinal.HomeScreen
@Composable
fun screen(travels: Travel, iconReason: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                        text = "Valor Total: ${travels.orcamento}R$",
                        style = MaterialTheme.typography.subtitle2
                    )
                }
            }
        }
    }

}

@Composable
fun ListTravels() {
    val application = LocalContext.current.applicationContext as Application
    val viewModel: RegisterNewTravelModel = viewModel(
        factory = RegisterNewTravelViewModelFactory(application)
    )
    viewModel.getTravels()

    val travels by viewModel.travels.collectAsState()

    println(travels)

    LazyColumn() {
        items(items = travels){
            val iconReason = when (it.reason) {
                0 -> R.drawable.bussines
                else -> R.drawable.lazer
            }
            screen(travels = it, iconReason = iconReason)
        }
        }

}
