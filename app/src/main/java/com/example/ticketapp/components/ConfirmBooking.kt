package com.example.ticketapp.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

//@Preview(showBackground = true)
@Composable
fun ConfirmBooking(navController: NavController, section: String, type: String, price: String) {
    val paddingValue = 16.dp
    val spacingValue = 24.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValue),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        BookingTitle()
        EventCard(section, type, price)
        Spacer(modifier = Modifier.height(spacingValue))
        SubtotalCard()
        Spacer(modifier = Modifier.height(spacingValue))
        ConfirmButton()
    }
}

@Composable
fun BookingTitle() {
    Text(
        text = "Ticket booking",
        modifier = Modifier.padding(75.dp),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        fontSize = 30.sp
    )
}

@Composable
fun EventCard(section: String, type: String, price: String) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(170.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Text("Les Ardentes", fontWeight = FontWeight.Bold)
            Text(text = "Friday • June 21 • 19.00 - 22.00")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Standart Ticket", color = Color.Gray)
            Text(text = "CA $${price}", fontWeight = FontWeight.Light)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "VIP $section", color = Color.Gray)
            Text(text = "CA $124.50", fontWeight = FontWeight.Light)
        }
    }
}

@Composable
fun SubtotalCard() {
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(170.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Subtotal", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(120.dp))
                Text("CA $282.00", fontWeight = FontWeight.Bold)
            }
            Text(text = "Including taxes", modifier = Modifier.padding(top = 1.dp))
            Text(text = "2 Tickets : CA $64.50 + CA $124.50", modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Composable
fun ConfirmButton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = {
                //rememberNavController()
                //navController.navigate("modal_page")
            },
            modifier = Modifier.width(200.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            )
        ) {
            Text("Pay now")
        }
    }
}