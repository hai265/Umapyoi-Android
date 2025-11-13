package com.example.uma.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.uma.ui.screens.HomeScreen

@Composable
fun UmaApp() {
    Scaffold(
        modifier = Modifier
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeScreen(
                modifier = Modifier.padding(top = it.calculateTopPadding())
            )
        }
    }
}