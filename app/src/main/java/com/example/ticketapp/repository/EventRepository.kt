package com.example.ticketapp.repository

import androidx.lifecycle.LiveData
import com.example.ticketapp.dao.EventDao
import com.example.ticketapp.models.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

// la classe EventRepository
class EventRepository(private val eventDao: EventDao) {

    val allEvents: Flow<List<Event>> = flow {
        emit(eventDao.getAllEvents())
    }.flowOn(Dispatchers.IO)

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

    fun getEventById(eventId: Int): LiveData<Event> {
        return eventDao.getEventById(eventId)
    }


}