package com.example.ticketapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ticketapp.dao.TicketDao
import com.example.ticketapp.models.Ticket
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Test

class TicketRepositoryTest {

    @Test
    fun insert_delegatesToDao() = runBlocking {
        val dao = FakeTicketDao()
        val repository = TicketRepository(dao)
        val ticket = sampleTicket()

        repository.insert(ticket)

        assertEquals(listOf(ticket), dao.insertedTickets)
    }

    @Test
    fun delete_delegatesToDao() = runBlocking {
        val dao = FakeTicketDao()
        val repository = TicketRepository(dao)
        val ticket = sampleTicket()

        repository.delete(ticket)

        assertEquals(listOf(ticket), dao.deletedTickets)
    }

    @Test
    fun getTicketsForEvent_returnsDaoLiveData() {
        val dao = FakeTicketDao()
        val repository = TicketRepository(dao)

        val result = repository.getTicketsForEvent(1)

        assertSame(dao.ticketsLiveData, result)
    }

    private fun sampleTicket() = Ticket(
        ticketId = 1,
        eventCreatorId = 1,
        typeTicket = "VIP",
        price = 99.99,
        seatNumber = 12,
        section = "A"
    )

    private class FakeTicketDao : TicketDao {
        val insertedTickets = mutableListOf<Ticket>()
        val deletedTickets = mutableListOf<Ticket>()
        val ticketsLiveData = MutableLiveData<List<Ticket>>()

        override fun getAllTickets(): List<Ticket> = emptyList()

        override fun insertTicket(ticket: Ticket) {
            insertedTickets += ticket
        }

        override fun deleteTicket(ticket: Ticket) {
            deletedTickets += ticket
        }

        override fun getTicketsByEventId(eventId: Int): List<Ticket> = emptyList()

        override fun getTicketsForEvent(eventId: Int): LiveData<List<Ticket>> = ticketsLiveData
    }
}
