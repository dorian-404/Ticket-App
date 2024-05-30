package com.example.ticketapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ticketapp.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Payementevent() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {

        Text(text = "Ticket booking",
            modifier = Modifier
                .padding(75.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            fontSize = 30.sp)


        Card(
            modifier = Modifier
                .width(300.dp)
                .height(170.dp),
            // shape = CutCornerShape(20.dp)
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(modifier=Modifier.padding(15.dp)) {
                Text("Payment Method",
                    fontWeight=Bold)
                Text(text = "Choose your payement")
                Row(verticalAlignment=Alignment.CenterVertically) {
                    RadioButton(selected=true,onClick={})
                    Image(painter = painterResource(id = R.drawable.master),contentDescription="Credit Card")
                    Text(text="**** **** 4321",modifier=Modifier.padding(start=8.dp))
                }
                Row(verticalAlignment=Alignment.CenterVertically) {
                    RadioButton(selected=false,onClick={})
                    Image(painter = painterResource(id = R.drawable.group),contentDescription="PayPal")
                    Text(text="**** **** 8569",modifier=Modifier.padding(start=8.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier
                .width(300.dp)
                .height(170.dp),
            // shape = CutCornerShape(20.dp)
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {

            Column(modifier=Modifier.padding(15.dp)) {
                Row(verticalAlignment=Alignment.CenterVertically) {
                    Text("Subtotal",
                        fontWeight=Bold)
                    Spacer(modifier = Modifier.width(120.dp))
                    Text("CA $282.00",
                        fontWeight=Bold)
                }
                Text(text="Including taxes",modifier=Modifier.padding(top=1.dp))
                Text(text="2 Tickets : CA $64.50 + CA $124.50",modifier=Modifier.padding(top=8.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Confirm Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {

                //rememberNavController()
                //navController.navigate("modal_page")
            },
                modifier = Modifier.width(200.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue
                )
            ) {
                Text("Pay now")
            }
        }

        }


    }

