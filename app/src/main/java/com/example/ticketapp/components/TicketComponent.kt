package com.example.ticketapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.ticketapp.R

//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TicketComponent(navController: NavController) {

    val showDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(15.dp)
            .background(Color(0xFFF5F5F5))
    ) {
        Text(
            text = "My Tickets",
            modifier = Modifier.padding(65.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 30.sp
        )

        TicketCard(
            imageResId = R.drawable.visu_festival_les_ardentes,
            title = "Festival Mural",
            date = "19 Juin 2024",
            time = "11.00 - 20.00",
            location = "Bathurst",
            tickets = "2 Tickets",
            navController = navController
        )
        Spacer(modifier = Modifier.height(16.dp))
        TicketCard(
            imageResId = R.drawable.festival,
            title = "Festival Mural",
            date = "19 Juin 2024",
            time = "11.00 - 20.00",
            location = "Bathurst",
            tickets = "2 Tickets",
            navController = navController
        )
    }

    // verif
    if (showDialog.value) {
        Dialog(
            onDismissRequest = { showDialog.value = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                EventDetails()
            }
        }
    }
}

@Composable
fun TicketCard(
    imageResId: Int,
    title: String,
    date: String,
    time: String,
    location: String,
    tickets: String,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable { navController.navigate("eventDetails") },
        shape = RoundedCornerShape(25.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Concert Image",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(25.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = title,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                IconTextRow(icon = Icons.Filled.DateRange, text = date)
                IconTextRow(icon = Icons.Filled.Info, text = time)
                IconTextRow(icon = Icons.Filled.LocationOn, text = location)
                IconTextRow(icon = Icons.Filled.Email, text = tickets)
            }
        }
    }
}

@Composable
fun IconTextRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(12.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = Color.Gray,
            fontSize = 14.sp
        )
    }
}