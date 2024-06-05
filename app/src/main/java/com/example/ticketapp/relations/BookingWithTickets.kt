package com.example.ticketapp.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.ticketapp.models.Booking
import com.example.ticketapp.models.Ticket


data class BookingWithTickets(
    @Embedded val booking: Booking,
    @Relation(
        parentColumn = "bookingId",
        entityColumn = "ticketId"
    )
    val tickets: List<Ticket>
)