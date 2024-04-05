package com.example.dtapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.dtapp.ui.NavigationSetup
import com.example.dtapp.ui.theme.DtappTheme
import com.example.dtapp.ui.theme.PurpleGrey80

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DtappTheme {
                Surface(
                    color = PurpleGrey80, modifier = Modifier.fillMaxSize()
                ) {
                    NavigationSetup()
                }
            }
        }
    }
}