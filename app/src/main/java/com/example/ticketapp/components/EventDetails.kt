package com.example.ticketapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ticketapp.R
import com.example.ticketapp.models.Event
import com.example.ticketapp.relations.EventWithTickets
import com.example.ticketapp.viewmodel.EventViewModel

@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EventDetails(navController: NavController, eventId: Int, eventViewModel: EventViewModel) {
    val eventWithTickets by eventViewModel.getEventWithTickets(eventId).observeAsState(null)

    eventWithTickets?.let { eventWithTickets ->
        val event = eventWithTickets.event
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item { EventImage(navController, event) } // Pass event to EventImage
            item { Spacer(modifier = Modifier.height(10.dp)) }
            item { EventDescription(event.description) }
            item { EventInformation(event.location, event.dateTime) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { BuyTicketButton(16.dp, navController) }
        }
    }
}

@Composable
fun EventImage(navController: NavController, event: Event) { // Add event as a parameter
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.festival),
            contentDescription = "img",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
        )
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
        IconButton(
            onClick = { /* Handle favorite click */ },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = "Favorite",
                tint = Color.White
            )
        }
        EventDetailsOverlay(event) // Pass event to EventDetailsOverlay
    }
}

@Composable
fun EventDetailsOverlay(event: Event) { // Add event as a parameter
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp) // Espacement de 4dp entre les éléments
        ) {
            Text(
                event.name, // Use event name
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            Text(
                event.name, // Use event price
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
        }
    }
}

@Composable
fun EventDescription(description: String) {
    Text(modifier = Modifier.padding(16.dp), text = "Description", style = MaterialTheme.typography.headlineSmall)
    Text(modifier = Modifier.padding(16.dp), text = description, style = MaterialTheme.typography.bodyMedium)
}

@Composable
fun EventInformation(location: String, dateTime: String) {
    Text(modifier = Modifier.padding(16.dp), text = "Informations", style = MaterialTheme.typography.headlineSmall)
    Text(modifier = Modifier.padding(16.dp), text = location, style = MaterialTheme.typography.bodyMedium)
    Text(modifier = Modifier.padding(16.dp), text = dateTime, style = MaterialTheme.typography.bodyMedium)
}

@Composable
fun BuyTicketButton(spacingValue: Dp, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacingValue),
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.width(spacingValue))
        Button(onClick = {
            navController.navigate("ticketBooking")
        },
            modifier = Modifier.width(200.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue
            )
        ) {
            Text("Buy ticket")
        }
    }
}