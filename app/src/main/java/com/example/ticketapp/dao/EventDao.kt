package com.example.ticketapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.ticketapp.models.Event
import com.example.ticketapp.models.User
import com.example.ticketapp.relations.EventWithBookings
import com.example.ticketapp.relations.EventWithTickets
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object pour les events
 * cette interface EventDao permet  de definir les requetes
 * pour les events pour aller chercher les events dans la base de donnees
 */

@Dao
interface EventDao {

    // Avoir la liste de tous les events
    @Query("SELECT * FROM events")
    fun getAllEvents(): List<Event>

    // Insérer un event
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEvent(event: Event)

    // Supprimer un event
    @Delete
    fun deleteEvent(event: Event)

    // Cette requete selectionne un event par son id
//    @Query("SELECT * FROM events WHERE eventId = :eventId")
//    fun getEventById(eventId: Int): Event

    // Cette requete permet de selectionner tous les evenements avec les tickets
    @Transaction
    @Query("SELECT * FROM events")
    fun getEventsWithTickets(): List<EventWithTickets>

    // Cette requete permet d'avoir tous les evenements qui ont ete reserves
    @Transaction
    @Query("SELECT * FROM events")
    fun getEventWithBookings(): List<EventWithBookings>

    // Ajoutez cette fonction
    @Transaction
    @Query("SELECT * FROM events WHERE eventId = :eventId")
    suspend fun getEventWithTickets(eventId: Int): EventWithTickets

    @Query("SELECT * FROM events WHERE eventId = :eventId")
    fun getEventById(eventId: Int): LiveData<Event>
}