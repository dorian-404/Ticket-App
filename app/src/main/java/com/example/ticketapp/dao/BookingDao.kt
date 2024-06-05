package com.example.ticketapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.ticketapp.models.Booking
import com.example.ticketapp.relations.BookingWithPayment
import com.example.ticketapp.relations.BookingWithTickets

@Dao
interface BookingDao {

    //
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(booking: Booking)

    // Avoir la liste de toutes les réservations
    @Query("SELECT * FROM bookings")
    suspend fun getAllBookings(): List<Booking>

    // Avoir une réservation avec les tickets
    @Transaction
    @Query("SELECT * FROM bookings WHERE bookingId = :bookingId")
    suspend fun getBookingWithTickets(bookingId: Int): List<BookingWithTickets>

    // Avoir une réservation avec le paiement
    @Transaction
    @Query("SELECT * FROM bookings WHERE bookingId = :bookingId")
    suspend fun getBookingWithPayment(bookingId: Int): List<BookingWithPayment>
}