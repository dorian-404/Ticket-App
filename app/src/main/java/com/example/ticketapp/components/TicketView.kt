import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.ticketapp.R
import com.example.ticketapp.components.TicketShape

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TicketView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0066FF)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Congratulations !!",
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Your ticket is confirmed",
            fontSize = 16.sp,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .background(Color.White, RoundedCornerShape(16.dp))
                .clip(TicketShape())
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.festival),
                    contentDescription = "Event Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Les Ardentes",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TicketDetailRow(label = "DATE", value = "23 JUIN 2024")
                    TicketDetailRow(label = "TIME", value = "19.00-22.00")
                    TicketDetailRow(label = "LOCATION", value = "Moncton")
                    TicketDetailRow(label = "SEAT", value = "VIP Ticket (Zone A)")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Canvas(modifier = Modifier.fillMaxWidth().height(1.dp)) {
                    drawLine(
                        color = Color.Gray,
                        start = androidx.compose.ui.geometry.Offset(0f, 0f),
                        end = androidx.compose.ui.geometry.Offset(size.width, 0f),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(50.dp)
                        .background(Color.Gray)
                ) {
                    // barcode
                    BasicText(
                        text = "|||||||||||||||||||||||||||||||||||",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { /* TODO: View details action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "View Details")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "View details", color = Color(0xFF0066FF))
            }
            Button(
                onClick = { /* TODO: Share ticket action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Icon(imageVector = Icons.Default.Share, contentDescription = "Share Ticket")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Share ticket", color = Color(0xFF0066FF))
            }
        }
    }
}

@Composable
fun TicketDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontWeight = FontWeight.Bold)
        Text(text = value)
    }
}


