package com.example.ticketapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tickets")
data class Ticket(
    @PrimaryKey(autoGenerate = true) val ticketId: Long = 0,
    val typeTicket: String,
    val price: Double,
    val seatNumber: Int,
    val section: Int,
    val nbreTickets: Int
)