package com.example.ticketapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.ticketapp.models.Ticket

@Dao
interface TicketDao {

    // Insertion d'un ticket OnConflictStrategy.REPLACE permet de remplacer un ticket existant
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicket(ticket: Ticket)
}