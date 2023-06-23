package com.example.appfinal.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense(
    @PrimaryKey(autoGenerate = true) val ExpenseId: Int = 0,
    val travelID: Int,
    @ColumnInfo(name = "descriptionExpense", defaultValue = "Cadastro da viagem") val descriptionExpense: String,
    val valueExpense: Float
) {
}