package com.example.ticketapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Long = 0,
    val firstName: String,
    val lastName: String,
    val password: String,
    val email: String
)