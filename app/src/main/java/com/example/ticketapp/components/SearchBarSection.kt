package com.example.ticketapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun SearchBarSection() {

    //  Utilisation de la fonction remember pour conserve l'état de la recherche
    // Utilisation de la fonction mutableStateOf pour faire le suivi de l'état du champ de recherche
    // Note : textState est une variable mutable qui contient la valeur du champ de recherche
        val textState = remember {
            mutableStateOf(TextFieldValue())
        }

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White),
            value = textState.value.text,
            onValueChange = { newValue ->
                textState.value = TextFieldValue(newValue)
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Icon")
            },
            placeholder = { Text("Search") },
            singleLine = true
        )
}