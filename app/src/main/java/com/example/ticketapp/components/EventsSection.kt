import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ticketapp.R
import com.example.ticketapp.viewmodel.EventViewModel

//@Preview(showSystemUi = true, showBackground = true)
@Composable
fun EventsSection(eventViewModel: EventViewModel, navController: NavController) {

    // Récupérer les événements de la base de données
    val events by eventViewModel.allEvents.observeAsState(emptyList())
    // Stocker l'etat de l'id de l'événement pour la navigation
    var navigateEventId by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            events.forEach { event ->
                EventCard(
                    imageRes = R.drawable.festival,
                    title = event.name,
                    dateAndLocation = event.date + " - " + event.location,
                    eventId = event.eventId,
                    navController = navController
                )
            }
        }
    }
    // Naviguer vers la page de détails de l'événement
    LaunchedEffect(navigateEventId) {
        navigateEventId?.let { navController.navigate("eventDetails/$it") }
    }
}

@Composable
fun EventCard(imageRes: Int, title: String, dateAndLocation: String, eventId: Long, navController: NavController) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(160.dp)
            .height(240.dp)
            .clickable { navController.navigate("eventDetails/$eventId") }
    ) {
        Box {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = dateAndLocation,
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}