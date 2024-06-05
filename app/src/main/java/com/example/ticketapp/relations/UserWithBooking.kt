package com.example.ticketapp.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.ticketapp.models.Booking
import com.example.ticketapp.models.Event
import com.example.ticketapp.models.User

data class UserWithBookings(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "eventId",
        associateBy = Junction(Booking::class)
    )
    val events: List<Event>
)
