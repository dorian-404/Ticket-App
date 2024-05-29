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

@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EventDetails(navController: NavController) {
    val paddingValue = 10.dp
    val spacingValue = 16.dp

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { EventImage(navController) }
        item { Spacer(modifier = Modifier.height(spacingValue)) }
        item { EventDescription(paddingValue = paddingValue) }
        item { EventInformation(paddingValue = paddingValue) }
        item { Spacer(modifier = Modifier.height(spacingValue)) }
        item { BuyTicketButton(spacingValue = spacingValue, navController = navController) }
    }
}

@Composable
fun EventImage(navController: NavController) {
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
        EventDetailsOverlay()
    }
}

@Composable
fun EventDetailsOverlay() {
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
                "Les Ardentes",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            Text(
                "CA\$45.00 - CA\$175.00",
                style = MaterialTheme.typography.bodySmall,
                color = Color.White
            )
        }
    }
}

@Composable
fun EventDescription(paddingValue: Dp) {
    Text(modifier = Modifier.padding(paddingValue), text = "Description", style = MaterialTheme.typography.headlineSmall)
    Text(modifier = Modifier.padding(paddingValue), text =
    "Every year, this event brings together internationally renowned artists and emerging talents, offering an eclectic programme ranging from rap and rock to electro and pop.",
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun EventInformation(paddingValue: Dp) {
    Text(modifier = Modifier.padding(paddingValue), text = "Informations", style = MaterialTheme.typography.headlineSmall)
    Text(modifier = Modifier.padding(paddingValue), text = "123 Rue Main, Street Moncton", style = MaterialTheme.typography.bodyMedium)
    Text(modifier = Modifier.padding(paddingValue), text = "19.00-22.00", style = MaterialTheme.typography.bodyMedium)
    Text(modifier = Modifier.padding(paddingValue), text = "21-23 Juin 2024", style = MaterialTheme.typography.bodyMedium)
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

