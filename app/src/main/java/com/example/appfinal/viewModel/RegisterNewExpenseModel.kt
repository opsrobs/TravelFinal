package com.example.appfinal.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfinal.entity.Expense
import com.example.appfinal.entity.Travel
import com.example.appfinal.repository.ExpenseRepository
import com.example.appfinal.repository.TravelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RegisterNewExpenseModel (private val expenseRepository: ExpenseRepository): ViewModel() {
    var description by mutableStateOf("")
    var datainicio by mutableStateOf("")
    var dataFim by mutableStateOf("")
    var valueExpense by  mutableStateOf(0f)
    var reason by  mutableStateOf(0)


    suspend fun register(travel: Int){
        val expense = Expense(ExpenseId = 0, travelID = travel, descriptionExpense = description, valueExpense = valueExpense )
        return expenseRepository.insertExpense(expense)

    }

    suspend fun registerValueOnExpense(travel: Int,desc:String, value: Float){
        val expense = Expense(ExpenseId = 0, travelID = travel, descriptionExpense = desc, valueExpense = value )
        return expenseRepository.insertExpense(expense)

    }

    val expenseTravel: MutableStateFlow<List<Expense>> = MutableStateFlow(emptyList())


    fun getExpenses(travelId:Int){
        viewModelScope.launch{
            val expenses = expenseRepository.getExpensesByTravel(travelId)
            expenseTravel.value = expenses
        }
    }

    fun updateExpenses(id: Int, orcamento: Float){
        expenseRepository.updateExpense(id, orcamento)
    }

//    val travels: MutableStateFlow<List<Travel>> = MutableStateFlow(emptyList())
//
//    fun getTravels(userId: Int) {
//        viewModelScope.launch {
//            val travelsRepo = travelRepository.getAllTravels(userId)
//            travels.value = travelsRepo
//        }
//    }



}