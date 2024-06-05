package com.example.ticketapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.ticketapp.models.Event
import com.example.ticketapp.models.User
import com.example.ticketapp.relations.EventWithBookings

@Dao
interface EventDao {
//    @Query("""
//        SELECT users.* FROM users
//        INNER JOIN bookings ON users.userId = bookings.userId
//        WHERE bookings.eventId = :eventId
//    """)
//    fun getUsersWhoBookedEvent(eventId: Long): List<User>

    // Récupération de tous les événements avec les réservations
    @Transaction
    @Query("SELECT * FROM events WHERE eventId = :eventId")
    suspend fun getEventWithBookings(eventId: Int): List<EventWithBookings>


    // Suppression d'un événement
    @Delete
    public fun deleteEvent(event: Event)


    // Récupération de tous les événements
    @Query("SELECT * FROM events")
    suspend fun getAllEvents(): List<Event>


    // Insertion d'un événement OnConflictStrategy.REPLACE permet de remplacer un événement existant
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event)
}