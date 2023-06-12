package com.example.appfinal.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.appfinal.entity.User
import com.example.appfinal.repository.UserRepository

class UserViewModel(private val user: UserRepository) : ViewModel() {
    var name by mutableStateOf("")
    var password by mutableStateOf("")
    var email by mutableStateOf("")


    suspend fun verify(): Boolean {
        val newUser = User(name = name, password = password, email = email)
        return user.getUsers(newUser)
    }
}
