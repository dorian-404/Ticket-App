package com.example.ticketapp.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.ticketapp.models.Booking
import com.example.ticketapp.models.Event
import com.example.ticketapp.models.User


data class EventWithBookings(
    @Embedded val event: Event,
    @Relation(
        parentColumn = "eventId",
        entityColumn = "userId",
        associateBy = Junction(Booking::class)
    )
    val users: List<User>
)
