package com.example.ticketapp.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.ticketapp.models.Booking
import com.example.ticketapp.models.Event
import com.example.ticketapp.models.Ticket

data class EventWithTickets(
    @Embedded val event: Event,
    @Relation(
        parentColumn = "eventId",
        entityColumn = "ticketId",
        associateBy = Junction(Booking::class)
    )
    val tickets: List<Ticket>
)
