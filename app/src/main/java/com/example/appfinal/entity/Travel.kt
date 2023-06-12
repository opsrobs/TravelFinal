package com.example.appfinal.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Travel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userID: Int,
    val destino: String,
    val dataInicio: String,
    val dataFinal: String,
    val orcamento: Float
) {
}