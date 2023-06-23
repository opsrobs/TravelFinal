package com.example.appfinal.repository

import com.example.appfinal.dao.TravelDao
import com.example.appfinal.dao.UserDao
import com.example.appfinal.entity.Travel
import com.example.appfinal.entity.TravelWithExpense
import kotlinx.coroutines.*

class TravelRepository(private val travelDao: TravelDao) {
    private val coroutine = CoroutineScope(Dispatchers.Main)


    suspend fun addTravel(travel: Travel) {
        travelDao.insert(travel)
    }

    suspend fun getAllTravels(userId: Int): List<Travel> {
        return travelDao.findAllByUserId(userId)

    }

    suspend fun getAllThinks(userId: Int): List<TravelWithExpense> {
        return travelDao.findAllDatas(userId)

    }

    suspend fun getTravelByName(destinnation: String): Int {
        println("\n\n ${destinnation}\n\n")
        val userExists = travelDao.findByDestino(destinnation).id
        return userExists;
    }

    suspend fun delete(travel: Travel){
        travelDao.delete(travel = travel )
    }

    suspend fun deleteById(id:Int){
        travelDao.deleteById(id)
    }

//    fun attATravel(id: Int,  orcamento: Float){
//        coroutine.launch(Dispatchers.IO){
//            travelDao.incrementExpenses(id,orcamento)
//        }
//    }

}