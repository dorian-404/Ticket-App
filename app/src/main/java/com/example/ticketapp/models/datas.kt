package com.example.ticketapp.models

data class Concerts(
    val nameArtiste: String,
    val location: String,
    val hour: String
)

data class Events(
    val nameEvent: String,
    val price: Double,
    val description: String,
    val informations: String,
    val dateLoc: String
)

data class Tickets(
    val idTicket: Int,
    val typeTicket: String,
    val price: String
)