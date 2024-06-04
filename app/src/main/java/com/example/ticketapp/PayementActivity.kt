package com.example.ticketapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.example.ticketapp.ui.theme.TicketAppTheme

class PaymentActivity : ComponentActivity() {
    private lateinit var paymentSheet: PaymentSheet
    private lateinit var paymentIntentClientSecret: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicketAppTheme {
                PaymentScreen()
            }
        }

        paymentIntentClientSecret = intent.getStringExtra("client_secret") ?: ""

        // Initialize PaymentConfiguration with your publishable key
        PaymentConfiguration.init(
            applicationContext,
            "pk_test_51PMBiqHhqi17tliDpXxmLr01lMit5ukwVxu3WjGhtftikvfdSIWxyG1vnOhebN5M4pexbpY9q8yaUtm4fIN2wHbK00RMV0y6Vj"
        )

        paymentSheet = PaymentSheet(this, ::onPaymentSheetResult)

        paymentSheet.presentWithPaymentIntent(paymentIntentClientSecret)
    }

    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when (paymentSheetResult) {
            is PaymentSheetResult.Completed -> {
                // Handle successful payment
            }
            is PaymentSheetResult.Canceled -> {
                // Handle canceled payment
            }
            is PaymentSheetResult.Failed -> {
                // Handle failed payment
            }
        }
    }
}

@Composable
fun PaymentScreen() {
    Text(text = "Processing payment...")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TicketAppTheme {
        PaymentScreen()
    }
}
