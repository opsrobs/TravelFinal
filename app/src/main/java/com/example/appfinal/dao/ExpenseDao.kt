package com.example.appfinal.dao

import androidx.room.*
import com.example.appfinal.entity.Expense

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insert(expense: Expense)

    @Update
    suspend fun update(expense: Expense)

    @Query("UPDATE expense SET valueExpense = :value WHERE ExpenseId = :id ")
    suspend fun incrementExpenses(id: Int, value: Float)

    @Delete
    suspend fun delete(expense: Expense)

    @Query("select * from expense e order by e.valueExpense")
    suspend fun findAll(): List<Expense>

    @Query("select * from expense e where e.travelID =:travelId order by e.valueExpense")
    suspend fun findAllByTravell(travelId: Int): List<Expense>

//    @Query("select * from expense e where e.travelID = :travelID order by e.valueExpense")
//    suspend fun findByTravels(travelID: Int): List<Expense>

}