package com.example.ticketapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ticketapp.dao.EventDao
import com.example.ticketapp.models.Event
import com.example.ticketapp.models.User
import com.example.ticketapp.relations.EventWithBookings
import com.example.ticketapp.relations.EventWithTickets
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test

class EventRepositoryTest {

    @Test
    fun insert_delegatesToDao() = runBlocking {
        val dao = FakeEventDao()
        val repository = EventRepository(dao)
        val event = sampleEvent()

        repository.insert(event)

        assertEquals(listOf(event), dao.insertedEvents)
    }

    @Test
    fun delete_delegatesToDao() = runBlocking {
        val dao = FakeEventDao()
        val repository = EventRepository(dao)
        val event = sampleEvent()

        repository.delete(event)

        assertEquals(listOf(event), dao.deletedEvents)
    }

    @Test
    fun getEventById_returnsDaoLiveData() {
        val dao = FakeEventDao()
        val repository = EventRepository(dao)

        val result = repository.getEventById(1)

        assertSame(dao.eventLiveData, result)
    }

    @Test
    fun allEvents_emitsEventsFromDao() = runBlocking {
        val events = listOf(
            sampleEvent(),
            sampleEvent(id = 2, name = "Festival Mural")
        )
        val dao = FakeEventDao(events = events)
        val repository = EventRepository(dao)

        val result = mutableListOf<List<Event>>()
        repository.allEvents.collect { emitted ->
            result += emitted
        }

        assertEquals(1, result.size)
        assertEquals(events, result.first())
    }

    private fun sampleEvent(
        id: Long = 1,
        name: String = "Les Ardentes"
    ) = Event(
        eventId = id,
        name = name,
        description = "Music festival",
        date = "2026-06-21",
        hour = "19:00",
        location = "Moncton"
    )

    private class FakeEventDao(
        private val events: List<Event> = emptyList()
    ) : EventDao {
        val insertedEvents = mutableListOf<Event>()
        val deletedEvents = mutableListOf<Event>()
        val eventLiveData = MutableLiveData<Event>()

        override fun getAllEvents(): List<Event> = events

        override fun insertEvent(event: Event) {
            insertedEvents += event
        }

        override fun deleteEvent(event: Event) {
            deletedEvents += event
        }

        override fun getEventsWithTickets(): List<EventWithTickets> = emptyList()

        override fun getEventWithBookings(): List<EventWithBookings> = emptyList()

        override suspend fun getEventWithTickets(eventId: Int): EventWithTickets {
            return EventWithTickets(sampleEvent(id = eventId.toLong()), emptyList())
        }

        override fun getEventById(eventId: Int): LiveData<Event> = eventLiveData
    }
}
