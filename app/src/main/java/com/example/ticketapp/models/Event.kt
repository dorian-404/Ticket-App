package com.example.ticketapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true) val eventId: Long = 0,
    val name: String,
    val description: String,
    val dateTime: String,
    val location: String
)