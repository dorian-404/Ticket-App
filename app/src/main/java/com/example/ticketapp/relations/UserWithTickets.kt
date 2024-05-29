package com.example.ticketapp.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.ticketapp.models.Booking
import com.example.ticketapp.models.User
import com.example.ticketapp.models.Ticket

data class UserWithTickets(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "ticketId",
        associateBy = Junction(
            value = Booking::class,
            parentColumn = "userId",
            entityColumn = "ticketId"
        )
    )
    val tickets: List<Ticket>
)