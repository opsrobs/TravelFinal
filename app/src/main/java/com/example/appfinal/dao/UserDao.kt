package com.example.appfinal.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.appfinal.entity.User

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("select * from user u order by u.name")
    suspend fun findAll(): List<User>

    @Query("select * from user u where u.name = :name")
    suspend fun findByName(name: String): User

    @Query("select * from user u where u.name = :name and u.password = :password")
    suspend fun findByCredentials(name: String, password:String): List<User>

}