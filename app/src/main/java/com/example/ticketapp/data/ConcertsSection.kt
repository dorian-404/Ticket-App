package com.example.ticketapp.data

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ticketapp.R

val concerts = listOf(

    Concerts(
        nameArtiste = "Damso",
        location = "Videotron",
        hour = "8.00 PM - 11.00 PM"
    ),
    Concerts(
        nameArtiste = "Tiakola",
        location = "Centre Bell",
        hour = "8.00 PM - 11.00 PM"
    ),
    Concerts(
        nameArtiste = "Gazo",
        location = "K.C Irving",
        hour = "8.00 PM - 11.00 PM"
    ),
    Concerts(
        nameArtiste = "Ninho",
        location = "Gill",
        hour = "8.00 PM - 11.00 PM"
    ),
)

@Preview(showSystemUi = true)
@Composable
fun ConcertsSectionData() {
    LazyRow {
        items(concerts.size) {index ->
            ConcertItem(index)
        }
    }
}

@Composable
fun ConcertItem(
    index: Int
) {
    val concert = concerts[index]
    var lasItemPaddingEnd = 0.dp
    if (index == concerts.size - 1) {
        lasItemPaddingEnd = 16.dp
    }

    Box(
        modifier = Modifier
            .padding(start = 16.dp)
    ) {

        Card(
            modifier = Modifier
                .padding(start = 16.dp, end = lasItemPaddingEnd)
                .width(200.dp)
                .height(270.dp),
            // shape = CutCornerShape(20.dp)
            elevation = CardDefaults.cardElevation(10.dp),
            //border = BorderStroke(3.dp,Color.Gray)
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.damso),
                    contentDescription = "null"
                )
                Text(
                    text = concert.nameArtiste,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(10.dp)
                )
                Text(
                    text = concert.location,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(10.dp),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray
                )
            }

        }
    }
}