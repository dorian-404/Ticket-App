package com.example.ticketapp.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "payment",
    foreignKeys = [
        ForeignKey(
            entity = Booking::class,
            parentColumns = ["bookingId"],
            childColumns = ["bookingId"],
            onDelete = CASCADE
        )
    ]
)
data class Payment(
    @PrimaryKey(autoGenerate = true) val paymentId: Long = 0,
    val bookingId: Long,
    val amount: Double,
    val paymentDate: String,
    val paymentMethod: String
)