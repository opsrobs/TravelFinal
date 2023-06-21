package com.example.appfinal.database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appfinal.dao.ExpenseDao
import com.example.appfinal.dao.TravelDao
import com.example.appfinal.dao.UserDao
import com.example.appfinal.entity.Expense
import com.example.appfinal.entity.Travel
import com.example.appfinal.entity.User

@Database(entities = [User::class, Travel::class, Expense::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun travelDaO(): TravelDao
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(application: Application): AppDatabase = INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(
                application.applicationContext,
                AppDatabase ::class.java,
                "meu-db03"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}