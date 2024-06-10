package com.example.ticketapp.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.ticketapp.models.Booking
import com.example.ticketapp.models.Payment

// Relation de 1 a 1 entre Booking et Payment : pour savoir quel paiement a ete effectue pour une reservation
data class BookingWithPayment(
    @Embedded val booking: Booking,
    @Relation(
        parentColumn = "bookingId",
        entityColumn = "bookingId"
    )
    val payment: Payment
)