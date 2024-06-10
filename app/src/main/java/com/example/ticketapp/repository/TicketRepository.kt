package com.example.ticketapp.repository

import androidx.lifecycle.LiveData
import com.example.ticketapp.dao.TicketDao
import com.example.ticketapp.models.Ticket

class TicketRepository(private val ticketDao: TicketDao) {

    // Récupération des tickets pour un événement spécifique
    fun getTicketsForEvent(eventId: Int): LiveData<List<Ticket>> {
        return ticketDao.getTicketsForEvent(eventId)
    }

    // Insertion d'un ticket
    suspend fun insert(ticket: Ticket) {
        ticketDao.insertTicket(ticket)
    }

    // Suppression d'un ticket
    suspend fun delete(ticket: Ticket) {
        ticketDao.deleteTicket(ticket)
    }
}