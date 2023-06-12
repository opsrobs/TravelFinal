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
}