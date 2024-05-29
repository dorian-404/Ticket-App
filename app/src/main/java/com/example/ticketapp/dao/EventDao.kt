package com.example.ticketapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ticketapp.models.Event
import com.example.ticketapp.models.User

@Dao
interface EventDao {
    @Query("""
        SELECT users.* FROM users
        INNER JOIN bookings ON users.userId = bookings.userId
        WHERE bookings.eventId = :eventId
    """)
    fun getUsersWhoBookedEvent(eventId: Long): List<User>

    // Insertion d'un événement OnConflictStrategy.REPLACE permet de remplacer un événement existant
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event)
}