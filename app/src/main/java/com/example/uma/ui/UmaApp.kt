package com.example.uma.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.uma.ui.screens.randomgame.RandomUmaScreen
import com.example.uma.ui.screens.umalist.UmaListScreen

@Composable
fun UmaApp() {
    Scaffold(
        modifier = Modifier
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            val modifier = Modifier.padding(top = it.calculateTopPadding())
            //TODO: Change to navigationController class (to display character list and random uma page)
//            RandomUmaScreen(
//                modifier = Modifier.padding(top = it.calculateTopPadding())
//            )
            UmaListScreen(modifier)
        }
    }
}