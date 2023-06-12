package com.example.appfinal.viewModel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfinal.entity.User
import com.example.appfinal.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterNewUserViewModel(private val userRepository: UserRepository) : ViewModel() {

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var passoword by mutableStateOf("")

    fun registrar() {
        val newUser = User(name = name, email = email, password = passoword)
        userRepository.addUser(newUser)
    }

    fun userTravel(user: String) = runBlocking {
        userRepository.findUser(user)
    }

    fun verifyIfUserExists() =
        runBlocking {
            val newUser = User(name = name, password = passoword, email = email)
            userRepository.getUsers(newUser)
        }

}