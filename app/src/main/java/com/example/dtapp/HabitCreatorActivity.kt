package com.example.dtapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dtapp.ui.theme.DtappTheme

class HabitCreatorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DtappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CreateHabit()
                }
            }
        }
    }

    @Composable
    fun CreateHabit() {
        var selectedSpinnerItem by remember { mutableStateOf("medium") }
        var selectedRadioItem by remember { mutableStateOf("running") }

        var nameText by remember { mutableStateOf("") }
        var descriptionText by remember { mutableStateOf("") }
        var amountText by remember { mutableStateOf("") }
        var periodicityText by remember { mutableStateOf("") }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HidingTextField(
                modifier = Modifier.fillMaxWidth(),
                text = "name of the habit",
                onTextChanged = { newValue ->
                    nameText = newValue
                }
            )

            HidingTextField(
                modifier = Modifier.fillMaxWidth(),
                text = "description",
                onTextChanged = { newValue ->
                    descriptionText = newValue
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Spinner(
                type = "priority: ",
                items = listOf("highest", "high", "medium", "low"),
                selectedItem = selectedSpinnerItem,
                onItemSelected = { item ->
                    selectedSpinnerItem = item
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            RadioButtons(
                items = listOf("morning exercise", "running", "meditation"),
                selectedItem = selectedRadioItem,
                onItemSelected = { newItem ->
                    selectedRadioItem = newItem
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                HidingTextField(
                    modifier = Modifier.weight(1f),
                    text = "amount",
                    onTextChanged = { newValue ->
                        amountText = newValue
                    }
                )

                HidingTextField(
                    modifier = Modifier.weight(1f),
                    text = "periodicity",
                    onTextChanged = { newValue ->
                        periodicityText = newValue
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.BottomCenter,
            ) {
                Button(onClick = {
                    val intent = Intent(this@HabitCreatorActivity, MainActivity::class.java).apply {
                        putExtra("priority", selectedSpinnerItem)
                        putExtra("type", selectedRadioItem)
                        putExtra("name", nameText)
                        putExtra("description", descriptionText)
                        putExtra("amount", amountText)
                        putExtra("periodicity", periodicityText)
                    }
                    startActivity(intent)
                }) {
                    Text(
                        text = "save",
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}