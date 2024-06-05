package com.example.ticketapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.ticketapp.models.User
import com.example.ticketapp.relations.UserWithBookings

@Dao
interface UserDao {

    // Ajouter un utilisateur
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    // Récupération de tous les utilisateurs
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    // Récupération d'un utilisateur avec les réservations
    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getUserWithBookings(userId: Int): List<UserWithBookings>
}