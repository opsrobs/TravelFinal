package com.example.appfinal.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true) val ExpenseId: Int = 0,
    val travelID: Int,
    val descriptionExpense: String,
    val valueExpense: Float
) {
}