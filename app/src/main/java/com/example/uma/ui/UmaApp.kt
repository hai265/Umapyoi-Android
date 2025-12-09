package com.example.uma.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
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
import com.example.uma.ui.screens.umalist.CharacterDetailsScreen
import com.example.uma.ui.screens.umalist.CharacterListScreen
import kotlinx.serialization.Serializable

sealed class UmaNavigables {
    open fun label(): String {
        return ""
    }

    @Composable
    open fun icon() {
    }

    //TODO: Move to separate sealed class?
    @Serializable
    data object SupportCards : UmaNavigables() {
        override fun label(): String = "Support Cards"

        @Composable
        override fun icon() {

        }
    }

    @Serializable
    data object UmaList : UmaNavigables() {
        override fun label(): String = "Characters"

        @Composable
        override fun icon() {
            //TODO
        }
    }

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
            BottomBar({
                navController.navigate(it)
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

//TODO: Introduct multiple back stacks for the character list and support list
// reason: want to go  back to list page if I tap on the tab again
@Composable
private fun NavGraph(
    navController: NavHostController,
    values: PaddingValues
) {
    val onTapSupportCard: (Int) -> Unit = { id: Int ->
        navController.navigate(UmaNavigables.SupportCardDetails(id))
    }
    NavHost(
        navController = navController,
        startDestination = UmaNavigables.UmaList,
        modifier = Modifier.padding(
            top = values.calculateTopPadding(),
            bottom = values.calculateBottomPadding()
        )
    ) {
        composable<UmaNavigables.UmaList> {
            CharacterListScreen(onTapCharacter = { id: Int ->
                navController.navigate(UmaNavigables.Character(id))
            })
        }
        composable<UmaNavigables.SupportCards> {
            SupportCardListScreen(onTapSupportCard = onTapSupportCard)
        }
        composable<UmaNavigables.Character> { backStackEntry ->
            val character: UmaNavigables.Character = backStackEntry.toRoute()
            CharacterDetailsScreen(id = character.id, onTapSupportCard = onTapSupportCard)
        }
        composable<UmaNavigables.SupportCardDetails> { backStackEntry ->
            SupportCardDetailsScreen()
        }
    }
}

//TODO: Better icons
//TODO: Selected
@Composable
fun BottomBar(onTabSelected: (UmaNavigables) -> Unit) {
    val bottomBarTabs = listOf(
        UmaNavigables.UmaList,
        UmaNavigables.SupportCards
    )
    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
        bottomBarTabs.forEach { tab ->
            NavigationBarItem(
                onClick = { onTabSelected(tab) },
                selected = false,
                icon = { tab.icon() },
                label = { Text(tab.label()) }
            )
        }
    }
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