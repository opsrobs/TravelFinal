package com.example.appfinal.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.appfinal.entity.Travel

@Dao
interface TravelDao {

    @Insert
    suspend fun insert(travel: Travel)

    @Update
    suspend fun update(travel: Travel)

    @Delete
    suspend fun delete(travel: Travel)

    @Query("select * from travel t order by t.dataInicio")
    suspend fun findAll(): List<Travel>

    @Query("select * from travel t where t.destino = :destino")
    suspend fun findByDestino(destino: String): Travel
}