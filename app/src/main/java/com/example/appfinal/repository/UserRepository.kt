package com.example.appfinal.repository

import com.example.appfinal.dao.UserDao
import com.example.appfinal.entity.User
import kotlinx.coroutines.*

class UserRepository(private val userDao: UserDao) {

    private val coroutine = CoroutineScope(Dispatchers.Main)

    fun addUser(user: User){
        coroutine.launch(Dispatchers.IO) {
            userDao.insert(user)
        }
    }

    suspend fun findUser (user: String): Int{
        val userExists = userDao.findByName(user).id
        return userExists;
    }

    suspend fun getUsers(user: User): Boolean = withContext(Dispatchers.IO) {
        val localUsers = userDao.findByCredentials(user.name, user.password)
        return@withContext !localUsers.isNullOrEmpty()
    }


}