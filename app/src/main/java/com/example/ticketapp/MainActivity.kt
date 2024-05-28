package com.example.ticketapp

import EventsSection
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ticketapp.components.HeaderComponent
import com.example.ticketapp.components.SearchBarSection
import com.example.ticketapp.data.ConcertsSectionData
import com.example.ticketapp.ui.theme.TicketAppTheme

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import com.example.ticketapp.components.TicketComponent

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
    }

    override fun onResume() {
        super.onResume()
        // Enregistrement de votre activité pour recevoir les événements NFC
        nfcAdapter?.enableForegroundDispatch(this, nfcPendingIntent, intentFiltersArray, null)
    }

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

                ConcertsSectionData()

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
        Text(
            text = "My Tickets",
            modifier = Modifier
                .padding(66.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 30.sp
        )

        TicketComponent()

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
