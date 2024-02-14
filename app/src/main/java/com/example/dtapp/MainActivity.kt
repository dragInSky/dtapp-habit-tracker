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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dtapp.ui.theme.DtappTheme

class MainActivity : ComponentActivity() {
    private var firstChange = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("dtapp:MainActivity", "onCreate")

        setContent {
            DtappTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenConfigCounter()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("dtapp:MainActivity", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("dtapp:MainActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("dtapp:MainActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("dtapp:MainActivity", "onStop")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("dtapp:MainActivity", "onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("dtapp:MainActivity", "onDestroy")
    }

    @Composable
    fun ScreenConfigCounter() {
        var counter by remember { mutableStateOf(intent.getIntExtra("counter", 0)) }

        LaunchedEffect(LocalConfiguration.current) {
            if (!firstChange)
                counter++
            firstChange = false
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(Color.White)
        ) {
            Text(
                text = "config changes: $counter",
                color = Color.Black,
                fontSize = 36.sp,
                fontWeight = FontWeight.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = {
                val intent = Intent(this@MainActivity, AdditiveActivity::class.java).apply {
                    putExtra("counter", counter)
                }
                startActivity(intent)
            }) {
                Text("go to square")
            }
        }
    }
}