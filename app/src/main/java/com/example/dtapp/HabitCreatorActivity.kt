package com.example.dtapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dtapp.ui.theme.AppColors
import com.example.dtapp.ui.theme.DtappTheme
import java.util.UUID
import kotlin.math.absoluteValue

class HabitCreatorActivity : ComponentActivity() {
    private val priorities = listOf("highest", "high", "medium", "low")
    private val types = listOf("study", "sport", "time-management", "health")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DtappTheme {
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    AppColors.gradientBottom,
                                    AppColors.gradientMid,
                                    AppColors.gradientTop
                                )
                            )
                        )
                ) {
                    CreateHabit()
                }
            }
        }
    }

    @Composable
    fun CreateHabit() {
        var selectedSpinner by remember {
            mutableStateOf(
                intent.getStringExtra("priority") ?: priorities[2]
            )
        }
        var selectedType by remember { mutableStateOf(intent.getStringExtra("type") ?: types[1]) }
        var nameText by remember { mutableStateOf(intent.getStringExtra("name") ?: "") }
        var descriptionText by remember {
            mutableStateOf(
                intent.getStringExtra("description") ?: ""
            )
        }
        var amountText by remember { mutableStateOf(intent.getStringExtra("times") ?: "") }
        var periodicityText by remember {
            mutableStateOf(
                intent.getStringExtra("period") ?: ""
            )
        }

        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            HidingTextField(
                text = nameText,
                placeHolder = "name of the habit",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                onTextChanged = { nameText = it }
            )

            HidingTextField(
                text = descriptionText,
                placeHolder = "description",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                onTextChanged = { descriptionText = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
            ) {
                Spinner(
                    text = "priority: ",
                    items = priorities,
                    selectedItem = selectedSpinner,
                    onItemSelected = { selectedSpinner = it }
                )
            }
            Divider(color = Color.Black, thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    )
            ) {
                RadioButtons(
                    items = types,
                    selectedItem = selectedType,
                    onItemSelected = { selectedType = it }
                )
            }
            Divider(color = Color.Black, thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            Row {
                HidingTextField(
                    text = amountText,
                    placeHolder = "times",
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White),
                    onTextChanged = { amountText = it }
                )

                HidingTextField(
                    text = periodicityText,
                    placeHolder = "period",
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White),
                    onTextChanged = { periodicityText = it }
                )
            }
        }

        SaveButton(
            selectedSpinner,
            selectedType,
            nameText,
            descriptionText,
            amountText,
            periodicityText
        )
    }

    @Composable
    fun SaveButton(
        selectedSpinner: String,
        selectedType: String,
        nameText: String,
        descriptionText: String,
        amountText: String,
        periodicityText: String
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.BottomCenter,
        ) {
            Button(
                onClick = {
                    val intent = Intent(this@HabitCreatorActivity, MainActivity::class.java).apply {
                        putExtra("isEdit", intent.getBooleanExtra("isEdit", false))
                        putExtra(
                            "id",
                            intent.getIntExtra("id", UUID.randomUUID().hashCode().absoluteValue)
                        )
                        putExtra("priority", selectedSpinner)
                        putExtra("type", selectedType)
                        if (nameText.isNotEmpty()) putExtra("name", nameText)
                        if (descriptionText.isNotEmpty()) putExtra("description", descriptionText)
                        if (amountText.isNotEmpty()) putExtra("times", amountText)
                        if (periodicityText.isNotEmpty()) putExtra("period", periodicityText)
                    }
                    startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColors.Darkest,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "save habit",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}