package com.example.uma.ui

import androidx.compose.foundation.layout.PaddingValues
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
import com.example.uma.ui.screens.supportcard.SupportCardDetailsScreen
import com.example.uma.ui.screens.supportcard.SupportCardListScreen
import com.example.uma.ui.screens.umalist.Profile
import com.example.uma.ui.screens.umalist.CharacterListScreen
import kotlinx.serialization.Serializable


sealed class UmaNavigables {
    @Serializable
    data object SupportCards : UmaNavigables()

    @Serializable
    data object UmaList : UmaNavigables()

    @Serializable
    data class Character(val id: Int) : UmaNavigables()

    @Serializable
    data class SupportCardDetails(val id: Int) : UmaNavigables()
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
                onClickSupportCards = {
                    navController.navigateSingleTopTo(UmaNavigables.SupportCards)
                })
        },
        modifier = Modifier
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            //TODO: Change to navigationController class (to display character list and random uma page)
            NavGraph(navController, it)
        }
    }
}

@Composable
private fun NavGraph(
    navController: NavHostController,
    values: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = UmaNavigables.UmaList,
        modifier = Modifier.padding(top = values.calculateTopPadding())
    ) {
        composable<UmaNavigables.UmaList> {
            CharacterListScreen(onTapCharacter = { id: Int ->
                navController.navigateSingleTopTo(UmaNavigables.Character(id))
            })
        }
        composable<UmaNavigables.SupportCards> {
            //TODO: Navigate to support card screen
            SupportCardListScreen(onTapSupportCard = { id: Int ->
                navController.navigateSingleTopTo(UmaNavigables.SupportCardDetails(id))
            })
        }
        composable<UmaNavigables.Character> { backStackEntry ->
            val character: UmaNavigables.Character = backStackEntry.toRoute()
            Profile(character.id)
        }
        composable<UmaNavigables.SupportCardDetails> { backStackEntry ->
            val supportCard: UmaNavigables.SupportCardDetails = backStackEntry.toRoute()
//            TODO: Pass in id by injection or savedstate?
            SupportCardDetailsScreen(supportCard.id)
        }
    }
}

//TODO: Better icons
@Composable
fun BottomBar(onClickCharacters: () -> Unit, onClickSupportCards: () -> Unit) {
    BottomAppBar(
        actions = {
            IconButton(onClick = onClickCharacters) {
                Text("Characters")
            }
            IconButton(onClick = onClickSupportCards) {
                Text("Support Cards")
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