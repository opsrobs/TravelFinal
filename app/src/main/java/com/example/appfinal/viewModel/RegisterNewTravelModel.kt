package com.example.appfinal.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfinal.entity.Travel
import com.example.appfinal.entity.TravelWithExpense
import com.example.appfinal.entity.User
import com.example.appfinal.repository.TravelRepository
import com.example.appfinal.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterNewTravelModel(private val travelRepository: TravelRepository) : ViewModel() {
    var destino by mutableStateOf("")
    var datainicio by mutableStateOf("")
    var dataFim by mutableStateOf("")
    var orcamento by mutableStateOf(0f)
    var reason by mutableStateOf(0)

    suspend fun register(userId: Int) {
        val newTravel = Travel(
            userID = userId,
            destino = destino,
            dataInicio = datainicio,
            dataFinal = dataFim,
            reason = reason
        )
        travelRepository.addTravel(newTravel)

    }


    val travels: MutableStateFlow<List<Travel>> = MutableStateFlow(emptyList())
    val travelsWithExpense: MutableStateFlow<List<TravelWithExpense>> =
        MutableStateFlow(emptyList())

    fun getTravels(userId: Int) {
        viewModelScope.launch {
            val travelsRepo = travelRepository.getAllTravels(userId)
            travels.value = travelsRepo
        }
    }


    fun getTravelsWithExpenses(userId: Int) {
        viewModelScope.launch {
            val travelsRepo = travelRepository.getAllThinks(userId)
            travelsWithExpense.value = travelsRepo
        }
    }

    fun deleteTravel(id: Int) {
        viewModelScope.launch {
            travelRepository.deleteById(id)
        }
    }

    fun getTravelByName(destino: String) = runBlocking {
        travelRepository.getTravelByName(destino)
    }

}