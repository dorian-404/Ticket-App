import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ticketapp.PaymentActivity
import com.example.ticketapp.models.Event
import com.example.ticketapp.models.Ticket
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@SuppressLint("RememberReturnType")
@Composable
fun ConfirmBooking(
    navController: NavController,
    eventName: String,
    eventDate: String,
    eventHour: String,
    ticketPrice: Double
) {
    val paddingValue = 16.dp
    val spacingValue = 24.dp

    var paymentIntentClientSecret by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    var total by remember { mutableStateOf(0.0) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        fetchPaymentIntent(total).onSuccess { clientSecret ->
            paymentIntentClientSecret = clientSecret
            Log.d("PaymentIntent", "Client Secret successfully set: $clientSecret")
        }.onFailure { paymentIntentError ->
            error = paymentIntentError.localizedMessage ?: paymentIntentError.message
            Log.e("PaymentIntent", "Error: $error")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValue),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        BookingTitle()
        EventCard(eventName, eventDate, eventHour, ticketPrice)
        Spacer(modifier = Modifier.height(spacingValue))
        SubtotalCard(ticketPrice)
        Spacer(modifier = Modifier.height(spacingValue))
        ConfirmButton(context, paymentIntentClientSecret)
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
fun EventCard(eventName: String, eventDate: String, eventHour: String, ticketPrice: Double) {
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
            Text(text = eventName, fontWeight = FontWeight.Bold)
            Text(text = eventDate)
            Spacer(modifier = Modifier.height(10.dp))
            //Text(text = ticketPrice: Double, color = Color.Gray)
            Text(text = "CA $ + $ticketPrice", fontWeight = FontWeight.Light)
            Spacer(modifier = Modifier.height(10.dp))
            //Text(text = "Quantity", color = Color.Gray)
            //Text(text = "CA $124.50", fontWeight = FontWeight.Light)
        }
    }
}

@Composable
fun SubtotalCard(ticketPrice: Double) {
    val TAX_RATE = 0.15 // 15% de taxe
    val tax = ticketPrice * TAX_RATE
    val total = ticketPrice + tax
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
                Text("CA $total", fontWeight = FontWeight.Bold)
            }
            Text(text = "Including taxes", modifier = Modifier.padding(top = 1.dp))
            Text(text = "2 Tickets : CA $ticketPrice + TAXES", modifier = Modifier.padding(top = 8.dp))
        }
    }
}

@Composable
fun ConfirmButton(context: android.content.Context, paymentIntentClientSecret: String?) {
    Button(
        onClick = {
            paymentIntentClientSecret?.let { clientSecret ->
                val intent = Intent(context, PaymentActivity::class.java).apply {
                    putExtra("client_secret", clientSecret)
                }
                Toast.makeText(context, "Starting PaymentActivity", Toast.LENGTH_SHORT).show()
                context.startActivity(intent)
            } ?: run {
                Toast.makeText(context, "PaymentIntent Client Secret is null", Toast.LENGTH_SHORT)
                    .show()
            }
        },
        modifier = Modifier.width(200.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue
        )
    ) {
        Text("Pay now")
    }
}


// Methode permettant de recuperer le client secret du serveur
private suspend fun fetchPaymentIntent(total: Double): Result<String> = suspendCoroutine { continuation ->
    val url = "http://10.0.2.2:4242/create-payment-intent"
    val ticketPrice = 15000 // valeur test a changer
    val ticketQuantity = 2
    val ticketBooked = """
        {
            "items": [
                 {"ticketId":"123",
                    "price": $ticketPrice,
                    "quantity": $ticketQuantity
                 }
            ]
        }
    """
    // requete post
    val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
    val requestBody = JSONObject()
        .put("total", total)
        .toString()
        .toRequestBody(mediaType)
    val request = Request.Builder()
        .url(url)
        .post(requestBody)
        .build()

    // creer une instance de OkHttpClient et ensuite on prepare la requete
    OkHttpClient()
        .newCall(request)
        .enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("PaymentIntent", "Failed to fetch PaymentIntent: ${e.message}")
                continuation.resume(Result.failure(e))
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    Log.e("PaymentIntent", "Error in response: ${response.message}")
                    continuation.resume(Result.failure(Exception(response.message)))
                } else {
                    val clientSecret = extractClientSecretFromResponse(response)

                    clientSecret?.let { secret ->
                        Log.d("PaymentIntent", "Client Secret: $secret")
                        continuation.resume(Result.success(secret))
                    } ?: run {
                        val error = Exception("Could not find payment intent client secret in response!")
                        Log.e("PaymentIntent", error.message.toString())
                        continuation.resume(Result.failure(error))
                    }
                }
            }
        })
}

// extraire le client secret de la reponse provenant du serveur
private fun extractClientSecretFromResponse(response: Response): String? {
    return try {
        val responseData = response.body?.string()
        Log.d("PaymentIntent", "Response Data: $responseData")
        val responseJson = responseData?.let { JSONObject(it) } ?: JSONObject()
        responseJson.getString("clientSecret")
    } catch (exception: JSONException) {
        Log.e("PaymentIntent", "JSON Exception: ${exception.message}")
        null
    }
}



