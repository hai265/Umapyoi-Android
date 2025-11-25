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
import androidx.navigation.toRoute
import com.example.uma.ui.screens.umalist.Profile
import com.example.uma.ui.screens.randomgame.RandomUmaScreen
import com.example.uma.ui.screens.umalist.UmaListScreen
import kotlinx.serialization.Serializable


sealed class UmaNavigables {
    @Serializable
    data object RandomUma : UmaNavigables()
    @Serializable
    data object UmaList : UmaNavigables()
    @Serializable
    class Character(val id: Int) : UmaNavigables()
}

//TODO: Add a top bar and buttons to navigate to different screens
@Composable
fun UmaApp(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = {
            BottomBar(
                onClickCharacters = {
                    navController.navigateSingleTopTo(UmaNavigables.UmaList)

                },
                onClickGames = {
                    navController.navigateSingleTopTo(UmaNavigables.RandomUma)
                })
        },
        modifier = Modifier
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            //TODO: Change to navigationController class (to display character list and random uma page)
            NavHost(
                navController = navController,
                startDestination = UmaNavigables.UmaList,
                modifier = Modifier.padding(top = it.calculateTopPadding())
            ) {
                composable<UmaNavigables.UmaList> {
                    UmaListScreen { id: Int ->
                        navController.navigateSingleTopTo(UmaNavigables.Character(id))
                    }
                }
                composable<UmaNavigables.RandomUma> {
                    RandomUmaScreen()
                }
                composable<UmaNavigables.Character> { backStackEntry ->
                    val character: UmaNavigables.Character = backStackEntry.toRoute()
                    Profile(character.id)
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

fun NavHostController.navigateSingleTopTo(route: UmaNavigables) =
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
    }