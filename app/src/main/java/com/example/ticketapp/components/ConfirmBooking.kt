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
fun ConfirmBooking(navController: NavController, section: String, type: String, price: String) {
    val paddingValue = 16.dp
    val spacingValue = 24.dp

    var paymentIntentClientSecret by remember { mutableStateOf<String?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        fetchPaymentIntent().onSuccess { clientSecret ->
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
        EventCard(section, type, price)
        Spacer(modifier = Modifier.height(spacingValue))
        SubtotalCard()
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
fun EventCard(section: String, type: String, price: String) {
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
            Text("Les Ardentes", fontWeight = FontWeight.Bold)
            Text(text = "Friday • June 21 • 19.00 - 22.00")
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Standard Ticket", color = Color.Gray)
            Text(text = "CA $${price}", fontWeight = FontWeight.Light)
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "VIP $section", color = Color.Gray)
            Text(text = "CA $124.50", fontWeight = FontWeight.Light)
        }
    }
}

@Composable
fun SubtotalCard() {
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
                Text("CA $282.00", fontWeight = FontWeight.Bold)
            }
            Text(text = "Including taxes", modifier = Modifier.padding(top = 1.dp))
            Text(text = "2 Tickets : CA $64.50 + CA $124.50", modifier = Modifier.padding(top = 8.dp))
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


private suspend fun fetchPaymentIntent(): Result<String> = suspendCoroutine { continuation ->
    val url = "http://10.0.2.2:4242/create-payment-intent"
    val ticketPrice = 15000 // Remplacez par la valeur réelle
    val ticketQuantity = 2
    val shoppingCartContent = """
        {
            "items": [
                 {"ticketId":"123",
                    "price": $ticketPrice,
                    "quantity": $ticketQuantity
                 }
            ]
        }
    """
    //
    val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
    val body = shoppingCartContent.toRequestBody(mediaType)
    val request = Request.Builder()
        .url(url)
        .post(body)
        .build()

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



