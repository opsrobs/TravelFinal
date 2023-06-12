package com.example.appfinal.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.appfinal.entity.Travel
import com.example.appfinal.entity.User
import com.example.appfinal.repository.TravelRepository
import com.example.appfinal.repository.UserRepository

class RegisterNewTravelModel(private val travelRepository: TravelRepository):ViewModel() {
    var destino by mutableStateOf("")
    var datainicio by mutableStateOf("")
    var dataFim by mutableStateOf("")
    var orcamento by  mutableStateOf(0f)


    fun register(userId: Int){
        val newTravel = Travel(userID = userId ,destino = destino, dataInicio = datainicio, dataFinal = dataFim, orcamento = orcamento)
        return travelRepository.addTravel(newTravel)

    }

}