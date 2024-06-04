package com.example.ticketapp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TicketBookingScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TicketBooking(navController)
        TicketDropdownMenu()
        TicketOptions(navController)
    }
}

@Composable
fun TicketBooking(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(50),
                contentPadding = ButtonDefaults.ContentPadding
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            Text(
                text = "Ticket booking",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4A60D0)
            )

            Button(
                onClick = { /*navController.navigate("tickets") */},
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(50),
                contentPadding = ButtonDefaults.ContentPadding
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close",
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
fun TicketDropdownMenu() {
    val expanded = remember { mutableStateOf(false) }
    val selectedTicket = remember { mutableStateOf("1 Ticket") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            Button(
                onClick = { expanded.value = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text(text = selectedTicket.value)
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown",
                    tint = Color.Black
                )
            }
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                DropdownMenuItem(
                    text = { Text("1 Ticket") },
                    onClick = {
                        selectedTicket.value = "1 Ticket"
                        expanded.value = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("2 Tickets") },
                    onClick = {
                        selectedTicket.value = "2 Tickets"
                        expanded.value = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("3 Tickets") },
                    onClick = {
                        selectedTicket.value = "3 Tickets"
                        expanded.value = false
                    }
                )
            }
        }

        Button(
            onClick = { /* TODO: Handle filter button click */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(50),
            border = BorderStroke(1.dp, Color.Black)
        ) {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "Filter",
                tint = Color.Black
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "Filters")
        }
    }
}


@Composable
fun TicketOptions(navController: NavController) {
    val tickets = listOf(
        TicketOption("Sec 007", "Eren", "$400.50"),
        TicketOption("Sec 12", "VIP Ticket", "$124.00"),
        TicketOption("Sec 13", "VIP Ticket", "$124.00")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tickets.forEach { ticket ->
            TicketCard(ticket) {
                // Navigate to ConfirmBooking screen with ticket information
                navController.navigate("confirmBooking/${ticket.section}/${ticket.type}/${ticket.price}")
            }
        }
    }
}

@Composable
fun TicketCard(ticket: TicketOption, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier.fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = ticket.section, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = ticket.type, fontSize = 14.sp, color = Color.Gray)
            }
            Text(text = ticket.price, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4A60D0))
        }
    }
}

data class TicketOption(val section: String, val type: String, val price: String)