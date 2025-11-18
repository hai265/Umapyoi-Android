package com.example.uma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.uma.ui.UmaApp
import com.example.uma.ui.theme.UmaTheme
import com.example.uma.ui.screens.HomeScreen
import com.example.uma.ui.screens.UmaViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            UmaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    UmaApp()
                }
            }
        }
    }
}