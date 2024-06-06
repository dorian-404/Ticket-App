package com.example.ticketapp.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.ticketapp.models.Booking
import com.example.ticketapp.models.Payment


data class BookingWithPayment(
    @Embedded val booking: Booking,
    @Relation(
        parentColumn = "bookingId",
        entityColumn = "bookingId"
    )
    val payment: Payment
)