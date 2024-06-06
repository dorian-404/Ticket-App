package com.example.ticketapp.viewmodel

import androidx.lifecycle.*
import com.example.ticketapp.models.Event
import com.example.ticketapp.relations.EventWithTickets
import com.example.ticketapp.repository.EventRepository
import kotlinx.coroutines.launch

// la ViewModel permet de gerer la partie logique de l'application
class EventViewModel(private val repository: EventRepository) : ViewModel() {

    val allEvents: LiveData<List<Event>> = repository.allEvents.asLiveData()

    // Insertion d'un événement
    fun insert(event: Event) = viewModelScope.launch {
        repository.insert(event)
    }
    // Suppression d'un événement
    fun delete(event: Event) = viewModelScope.launch {
        repository.delete(event)
    }

    // Récupération d'un événement avec les tickets
    fun getEventWithTickets(eventId: Int): LiveData<EventWithTickets> {
        val result = MutableLiveData<EventWithTickets>()
        viewModelScope.launch {
            result.postValue(repository.getEventWithTickets(eventId))
        }
        return result
    }
}

// Factory pour la création d'une instance de EventViewModel
class EventViewModelFactory(private val repository: EventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EventViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EventViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
