package com.example.ticketapp.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.ticketapp.models.Booking
import com.example.ticketapp.models.Event
import com.example.ticketapp.models.User

// Relation de 1 a plusieurs entre User et Booking : pour savoir quels events a reserve un user
data class UserWithBookings(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "eventId",
        associateBy = Junction(Booking::class)
    )
    val events: List<Event>
)
