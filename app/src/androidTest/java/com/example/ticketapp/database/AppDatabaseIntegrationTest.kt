package com.example.ticketapp.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ticketapp.dao.EventDao
import com.example.ticketapp.dao.TicketDao
import com.example.ticketapp.models.Event
import com.example.ticketapp.models.Ticket
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseIntegrationTest {

    private lateinit var database: AppDatabase
    private lateinit var eventDao: EventDao
    private lateinit var ticketDao: TicketDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        eventDao = database.eventDao()
        ticketDao = database.ticketDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertEvent_thenGetAllEvents_returnsInsertedEvent() {
        val event = sampleEvent()

        eventDao.insertEvent(event)

        val events = eventDao.getAllEvents()
        assertEquals(1, events.size)
        assertEquals(event, events.first())
    }

    @Test
    fun insertTickets_thenGetTicketsByEventId_returnsOnlyMatchingTickets() {
        val event = sampleEvent()
        val matchingTicket = sampleTicket(ticketId = 1, eventId = 1, seatNumber = 10)
        val otherTicket = sampleTicket(ticketId = 2, eventId = 2, seatNumber = 20)

        eventDao.insertEvent(event)
        ticketDao.insertTicket(matchingTicket)
        ticketDao.insertTicket(otherTicket)

        val tickets = ticketDao.getTicketsByEventId(1)

        assertEquals(1, tickets.size)
        assertEquals(matchingTicket, tickets.first())
    }

    @Test
    fun insertEventAndTickets_thenGetEventWithTickets_returnsJoinedData() = runBlocking {
        val event = sampleEvent()
        val vipTicket = sampleTicket(ticketId = 1, eventId = 1, seatNumber = 10, section = "VIP")
        val regularTicket = sampleTicket(ticketId = 2, eventId = 1, seatNumber = 11, section = "Regular")

        eventDao.insertEvent(event)
        ticketDao.insertTicket(vipTicket)
        ticketDao.insertTicket(regularTicket)

        val eventWithTickets = eventDao.getEventWithTickets(1)

        assertNotNull(eventWithTickets)
        assertEquals(event, eventWithTickets.event)
        assertEquals(2, eventWithTickets.tickets.size)
        assertEquals(listOf(vipTicket, regularTicket), eventWithTickets.tickets)
    }

    private fun sampleEvent() = Event(
        eventId = 1,
        name = "Les Ardentes",
        description = "Music festival",
        date = "2026-06-21",
        hour = "19:00",
        location = "Moncton"
    )

    private fun sampleTicket(
        ticketId: Long,
        eventId: Long,
        seatNumber: Int,
        section: String = "A"
    ) = Ticket(
        ticketId = ticketId,
        eventCreatorId = eventId,
        typeTicket = "Standard",
        price = 49.99,
        seatNumber = seatNumber,
        section = section
    )
}
