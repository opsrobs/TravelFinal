package com.example.appfinal.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.appfinal.entity.Travel
import com.example.appfinal.entity.TravelWithExpense

@Dao
interface TravelDao {

    @Insert
    suspend fun insert(travel: Travel)

    @Update
    suspend fun update(travel: Travel)

//    @Query("UPDATE Travel SET orcamento = :newOrcamento WHERE id = :id ")
//    suspend fun incrementExpenses(id: Int, newOrcamento: Float)


    @Delete
    suspend fun delete(travel: Travel)

    @Query("delete from travel where id = :item ")
    suspend fun deleteById(item: Int)

    @Query("select * from travel t order by t.dataInicio")
    suspend fun findAll(): List<Travel>

    @Query("select * from travel t where t.userID = :userId GROUP BY t.destino order by t.dataInicio")
    suspend fun findAllByUserId(userId: Int): List<Travel>

    @Query("select * from travel t where t.destino = :destino")
    suspend fun findByDestino(destino: String): Travel

    @Transaction
    @Query("SELECT t.*, e.*, SUM(e.valueExpense) as valueExpense FROM travel t INNER JOIN expense e ON t.id = e.travelID AND t.userID = :userId GROUP BY t.destino")
    suspend fun findAllDatas(userId: Int): List<TravelWithExpense>

}