package com.example.appfinal.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appfinal.database.AppDatabase
import com.example.appfinal.repository.ExpenseRepository
import com.example.appfinal.repository.TravelRepository

class RegisterNewExpenseFactory(val application: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val dao = AppDatabase.getDatabase(application).expenseDao()
            val expenseRepository = ExpenseRepository(dao)
            return RegisterNewExpenseModel(expenseRepository) as T
        }
    }
