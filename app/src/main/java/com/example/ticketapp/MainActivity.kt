package com.example.ticketapp

import ConfirmBooking
import EventsSection
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ticketapp.components.HeaderComponent
import com.example.ticketapp.components.SearchBarSection
import com.example.ticketapp.ui.theme.TicketAppTheme

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ticketapp.components.EventDetails
import com.example.ticketapp.components.TicketBookingScreen
import com.example.ticketapp.components.TicketComponent
import com.example.ticketapp.data.ConcertSection
import com.example.ticketapp.database.AppDatabase
import com.example.ticketapp.models.Event
import com.stripe.android.PaymentConfiguration
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    // Declaration des proprietes
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var nfcPendingIntent: PendingIntent
    private lateinit var intentFiltersArray: Array<IntentFilter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Definition des menu de navigation
            var selected by remember { mutableStateOf(0) }
            TicketAppTheme {
                Scaffold(
                    topBar = {

                    },
                    content = { paddingValues ->
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            color = MaterialTheme.colorScheme.background
                        ) {

                            when (selected) {
                                0 -> HomeScreen()
                                1 -> TicketScreen()
                                2 -> FavoritesScreen()
                                3 -> ProfileScreen()
                            }

                        }
                    },
                    bottomBar = {
                        NavigationBar {
                            bottomNavItems.forEachIndexed { index, bottomNavItem ->
                                NavigationBarItem(
                                    selected = index == selected,
                                    onClick = { selected = index },
                                    icon = {
                                        Icon(
                                            imageVector = if (index == selected) bottomNavItem.selectedIcon else bottomNavItem.unselectedIcon,
                                            contentDescription = bottomNavItem.title,
                                        )
                                    },
                                    label = { Text(text = bottomNavItem.title, color = Color.Gray) }
                                )
                            }
                        }
                    },
                )
            }
        }

        // Appel de la fonction populateDatabase
        populateDatabase()

        // initialisation de l'adaptateur NFC
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        // Création d'un PendingIntent pour lancer  l'activité lorsque une étiquette NFC est détectée
        val nfcIntent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        nfcPendingIntent = PendingIntent.getActivity(
            this, 0, nfcIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        // Création d'un IntentFilter pour intercepter les étiquettes NFC découvertes
        val ndefIntentFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED).apply {
            try {
                addDataType("*/*")
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                throw RuntimeException("Malformed MIME type", e)
            }
        }
        intentFiltersArray = arrayOf(ndefIntentFilter)

        // Initialisation de la configuration de paiement Stripe
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51PMBiqHhqi17tliDpXxmLr01lMit5ukwVxu3WjGhtftikvfdSIWxyG1vnOhebN5M4pexbpY9q8yaUtm4fIN2wHbK00RMV0y6Vj"
        )
    }

    // lorsque l'appli est en cours d'execution
    override fun onResume() {
        super.onResume()
        // Enregistrement de votre activité pour recevoir les événements NFC
        nfcAdapter?.enableForegroundDispatch(this, nfcPendingIntent, intentFiltersArray, null)
    }

    // lorsque l'appli est en pause
    override fun onPause() {
        super.onPause()
        // Désenregistrement de l'activité pour recevoir les événements NFC
        nfcAdapter?.disableForegroundDispatch(this)
    }


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        // Traitement des intents NFC
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            val messages = rawMessages?.map { it as NdefMessage }

        }
    }

    private fun populateDatabase() {
        val eventDao = AppDatabase.getDatabase(this).eventDao()

        // Créez des instances de Event
        val event1 = Event(eventId = 1, name = "Nom de l'événement", description = "Loremdjdeere", dateTime = "eee", location = "Terre")
        val event2 = Event(eventId = 2,  name = "Nom de l'événement", description = "Loremdjdeere", dateTime = "eee", location = "Location 2")
        GlobalScope.launch {
            // Inserenles événements dans la base de données
//            eventDao.insertEvent(event1)
//            eventDao.insertEvent(event2)
            // Recupere les events de ma db
            val events = eventDao.getAllEvents()

            // Supprimer un événement
              eventDao.deleteEvent(event1)


            // Écrire les événements dans Logcat
            for (event in events) {
                Log.d("Database", "Event: $event")
            }
        }
    }
    @Preview(showSystemUi = true)
    @Composable
    fun HomeScreen() {

        LazyColumn {

            item {
                HeaderComponent()

                SearchBarSection()

                // Spacer
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Upcoming Events",
                    modifier = Modifier
                        .padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 20.sp
                )

                ConcertSection()

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recommended Events",
                        modifier = Modifier
                            .padding(16.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    TextButton(onClick = { /* Handle See All click */ }) {
                        Text(
                            text = "See all",
                            color = Color.Blue,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            item { EventsSection() }

            item { EventsSection() }
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    fun TicketScreen() {

        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "ticketComponent") {
            composable("ticketComponent") { TicketComponent(navController) }
            composable("eventDetails") { EventDetails(navController) }
            composable("ticketBooking") { TicketBookingScreen(navController) }
            composable("confirmBooking/{section}/{type}/{price}") { backStackEntry ->
                val section = backStackEntry.arguments?.getString("section")
                val type = backStackEntry.arguments?.getString("type")
                val price = backStackEntry.arguments?.getString("price")
                if (section != null && type != null && price != null) {
                    ConfirmBooking(navController, section, type, price)
                } else {
                    // Handle error
                }
            }
            //composable("tickets") { TicketsScreen(navController) }
        }
    }

    @Composable
    fun FavoritesScreen() {
    }

    @Composable
    fun ProfileScreen() {
    }

    val bottomNavItems = listOf(
        BottomNavItem(
            title = "Home",
            route = "home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        BottomNavItem(
            title = "My Tickets",
            route = "tickets",
            selectedIcon = Icons.Filled.MailOutline,
            unselectedIcon = Icons.Outlined.MailOutline
        ),
        BottomNavItem(
            title = "Favorites",
            route = "favorites",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.FavoriteBorder
        ),
        BottomNavItem(
            title = "Profile",
            route = "profile",
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle
        )
    )

    data class BottomNavItem(
        val title: String,
        val route: String,
        val selectedIcon: ImageVector,
        val unselectedIcon: ImageVector
    )
}
