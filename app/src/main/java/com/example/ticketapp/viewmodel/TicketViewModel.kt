package com.example.ticketapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ticketapp.models.Ticket
import com.example.ticketapp.repository.TicketRepository
import kotlinx.coroutines.launch

class TicketViewModel(private val repository: TicketRepository) : ViewModel() {

    // Récupération des tickets pour un événement spécifique
    fun getTicketsForEvent(eventId: Int): LiveData<List<Ticket>> {
        return repository.getTicketsForEvent(eventId)
    }

    // Insertion d'un ticket
    fun insert(ticket: Ticket) = viewModelScope.launch {
        repository.insert(ticket)
    }

    // Suppression d'un ticket
    fun delete(ticket: Ticket) = viewModelScope.launch {
        repository.delete(ticket)
    }
}

// Factory pour la création d'une instance de TicketViewModel

class TicketViewModelFactory(private val repository: TicketRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TicketViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TicketViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}