package com.example.appfinal.repository

import com.example.appfinal.dao.ExpenseDao
import com.example.appfinal.dao.TravelDao
import com.example.appfinal.entity.Expense
import com.example.appfinal.entity.Travel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExpenseRepository(private val expenseDao: ExpenseDao) {
    private val coroutine = CoroutineScope(Dispatchers.Main)

    suspend fun insertExpense(expense: Expense){
            expenseDao.insert(expense)
    }

//    suspend fun getAllExpenses(userId: Int): List<Expense> {
//        return withContext(Dispatchers.IO) {
//            expenseDao.findByTravels(userId)
//        }
//    }

    fun updateExpense(id: Int,  orcamento: Float){
        coroutine.launch(Dispatchers.IO){
            expenseDao.incrementExpenses(id,orcamento)
        }
    }

    suspend fun getExpensesByTravel(travelId:Int):List<Expense> {
        return expenseDao.findAllByTravell(travelId)
    }
}