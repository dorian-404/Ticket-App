package com.example.ticketapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.ticketapp.models.Booking
import com.example.ticketapp.relations.BookingWithPayment

/**
 * Data Access Object pour les réservations
 * cette interface BookingDao permet de definir les requetes
 * pour chercher les informations des réservations dans la base de donnees
 */

@Dao
interface BookingDao {

    // Ajouter une réservation
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(booking: Booking)

    // Avoir la liste de toutes les réservations
    @Query("SELECT * FROM bookings")
    suspend fun getAllBookings(): List<Booking>

    // Avoir une réservation avec le paiement effectué
    @Transaction
    @Query("SELECT * FROM bookings")
    fun getBookingAndPayment(): List<BookingWithPayment>

}