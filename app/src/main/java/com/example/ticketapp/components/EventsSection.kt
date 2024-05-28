import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.ticketapp.R

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun EventsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            EventCard(
                imageRes = R.drawable.festival,
                title = "Les Ardentes",
                dateAndLocation = "Jun 19 - Moncton"
            )
//            Spacer(modifier = Modifier.width(1.dp))
            EventCard(
                imageRes = R.drawable.visu_festival_les_ardentes,
                title = "Festival Mural",
                dateAndLocation = "Jun 19 - Moncton"
            )
        }
    }
}

@Composable
fun EventCard(imageRes: Int, title: String, dateAndLocation: String) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(160.dp)
            .height(240.dp)
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