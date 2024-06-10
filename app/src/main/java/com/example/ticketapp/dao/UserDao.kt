package com.example.ticketapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.ticketapp.models.User
import com.example.ticketapp.relations.UserWithBookings

/**
 * Data Access Object pour les users
 * cette interface UserDao permet de definir les requetes
 * pour chercher les informations des users dans la base de donnees
 */

@Dao
interface UserDao {

    // Ajouter un utilisateur
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    // Récupération de tous les utilisateurs
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>

    // Cette requete permet de sekectionner tous les users ayant des reservations
    @Transaction
    @Query("SELECT * FROM users")
    suspend fun getUserWithBookings(): List<UserWithBookings>
}