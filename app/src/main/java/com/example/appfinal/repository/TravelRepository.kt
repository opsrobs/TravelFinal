package com.example.appfinal.repository

import com.example.appfinal.dao.TravelDao
import com.example.appfinal.dao.UserDao
import com.example.appfinal.entity.Travel
import kotlinx.coroutines.*

class TravelRepository(private val travelDao: TravelDao) {
    private val coroutine = CoroutineScope(Dispatchers.Main)

    fun addTravel(travel: Travel){
        coroutine.launch(Dispatchers.IO) {
            travelDao.insert(travel)
        }
    }

    suspend fun getAllTravels(userId: Int): List<Travel> {
        return withContext(Dispatchers.IO) {
            travelDao.findAllByUserId(userId)
        }
    }

    fun attATravel(id: Int,  orcamento: Float){
        coroutine.launch(Dispatchers.IO){
            travelDao.incrementExpenses(id,orcamento)
        }
    }

}