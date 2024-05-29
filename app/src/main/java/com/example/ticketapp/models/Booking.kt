package com.example.ticketapp.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey


// Contraints for the Booking entity
@Entity(
    tableName = "bookings",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Event::class,
            parentColumns = ["eventId"],
            childColumns = ["eventId"],
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Ticket::class,
            parentColumns = ["ticketId"],
            childColumns = ["ticketId"],
            onDelete = CASCADE
        )
    ]
)
data class Booking(
    @PrimaryKey(autoGenerate = true) val bookingId: Long = 0,
    val userId: Long,
    val eventId: Long,
    val ticketId: Long,
    val quantityTickets: Int
)