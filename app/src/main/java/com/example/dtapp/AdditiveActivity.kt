package com.example.dtapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dtapp.ui.theme.DtappTheme

class AdditiveActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("dtapp:AdditiveActivity", "onCreate")

        setContent {
            DtappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenCalculator()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("dtapp:AdditiveActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("dtapp:AdditiveActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("dtapp:AdditiveActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("dtapp:AdditiveActivity", "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("dtapp:AdditiveActivity", "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("dtapp:AdditiveActivity", "onDestroy")
    }

    @Composable
    fun ScreenCalculator() {
        val counter = intent.getIntExtra("counter", 0)

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(Color.Black)
        ) {
            Text(
                text = "square is: ${counter * counter}",
                color = Color.White,
                fontSize = 54.sp,
                fontWeight = FontWeight.Light
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = {
                val intent = Intent(this@AdditiveActivity, MainActivity::class.java).apply {
                    putExtra("counter", counter)
                }
                startActivity(intent)
            }) {
                Text("back to config")
            }
        }
    }
}