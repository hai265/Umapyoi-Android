package com.example.uma.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.uma.ui.screens.randomgame.RandomUmaScreen
import com.example.uma.ui.screens.umalist.UmaListScreen

enum class UmaScreen {
    RandomUma,
    UmaList
}

//TODO: Add a top bar and buttons to navigate to different screens
@Composable
fun UmaApp(navController: NavHostController = rememberNavController()) {
    Scaffold(
        modifier = Modifier
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            val modifier = Modifier.padding(top = it.calculateTopPadding())
            //TODO: Change to navigationController class (to display character list and random uma page)
            NavHost(
                navController = navController,
                startDestination = UmaScreen.UmaList.name,
                modifier = Modifier
            ) {
                composable(route = UmaScreen.UmaList.name) {
                    UmaListScreen()
                }
                composable(route = UmaScreen.RandomUma.name) {
                    RandomUmaScreen()
                }
            }
        }
    }
}