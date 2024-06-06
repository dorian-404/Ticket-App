package com.example.ticketapp.repository

import com.example.ticketapp.dao.EventDao
import com.example.ticketapp.models.Event
import kotlinx.coroutines.flow.Flow

// la classe EventRepository
class EventRepository(private val eventDao: EventDao) {

    val allEvents: Flow<List<Event>> = eventDao.getAllEvents()

    // Insertion d'un événement
    suspend fun insert(event: Event) {
        eventDao.insertEvent(event)
    }
    // Suppression d'un événement
    suspend fun delete(event: Event) {
        eventDao.deleteEvent(event)
    }
    // Récupération d'un événement avec les tickets
    suspend fun getEventWithTickets(eventId: Int) = eventDao.getEventWithTickets(eventId)


}