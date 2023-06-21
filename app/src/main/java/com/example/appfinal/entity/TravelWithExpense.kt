package com.example.appfinal.entity

data class TravelWithExpense(
    val id: Int = 0,
    val userID: Int,
    val destino: String,
    val dataInicio: String,
    val dataFinal: String,
    val reason: Int,
    val ExpenseId: Int = 0,
    val travelID: Int,
    var descriptionExpense: String,
    val valueExpense: Float
) {
}