package com.example.ticketapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ticketapp.models.Ticket


/**
 * Data Access Object pour les tickets
 * cette interface TicketDao permet  de definir les requetes p
 * our les tickets pour aller chercher
 * les tickets dans la base de donnees
 */
@Dao
interface TicketDao {

    // Cette requete selectionne tous les tickets pour tous les evenements
    @Query("SELECT * FROM tickets")
    fun getAllTickets(): List<Ticket>

    // Cette requete insere un ticket
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTicket(ticket: Ticket)

    // Cette requete supprime un ticket
    @Delete
    fun deleteTicket(ticket: Ticket)

    // Cette requete selectionne tous les tickets d'un evenement en lui passant l'id de l'evenement
    @Query("SELECT * FROM tickets WHERE eventCreatorId = :eventId")
    fun getTicketsByEventId(eventId: Int): List<Ticket>

    @Query("SELECT * FROM tickets WHERE eventCreatorId = :eventId")
    fun getTicketsForEvent(eventId: Int): LiveData<List<Ticket>>
}