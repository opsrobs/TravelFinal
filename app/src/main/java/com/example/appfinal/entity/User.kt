package com.example.appfinal.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity //se for mudar nome tabela: @Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val email: String,
    val password: String
) {

}