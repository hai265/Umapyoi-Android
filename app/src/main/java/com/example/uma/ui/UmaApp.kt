package com.example.uma.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
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
        bottomBar = {
            BottomBar(
                onClickCharacters = {
                    navController.navigateSingleTopTo(UmaScreen.UmaList.name)

                },
                onClickGames = {
                    navController.navigateSingleTopTo(UmaScreen.RandomUma.name)
                })
        },
        modifier = Modifier
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            //TODO: Change to navigationController class (to display character list and random uma page)
            NavHost(
                navController = navController,
                startDestination = UmaScreen.UmaList.name,
                modifier = Modifier.padding(top = it.calculateTopPadding())
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

//TODO: Better icons
@Composable
fun BottomBar(onClickCharacters: () -> Unit, onClickGames: () -> Unit) {
    BottomAppBar(
        actions = {
            IconButton(onClick = onClickCharacters) {
                Text("Characters")
            }
            IconButton(onClick = onClickGames) {
                Text("Games")
            }
        }
    )
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
    }