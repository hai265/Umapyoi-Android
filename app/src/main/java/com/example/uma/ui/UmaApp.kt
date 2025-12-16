package com.example.uma.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.uma.R
import com.example.uma.ui.screens.character.CharacterDetailsScreen
import com.example.uma.ui.screens.character.CharacterListScreen
import com.example.uma.ui.screens.supportcard.SupportCardDetailsScreen
import com.example.uma.ui.screens.supportcard.SupportCardListScreen
import kotlinx.serialization.Serializable

sealed class UmaNavigables {
    @Serializable
    object SupportCards : UmaNavigables()

    @Serializable
    object CharacterList : UmaNavigables()

    @Serializable
    data class Character(val id: Int) : UmaNavigables()

    @Serializable
    data class SupportCardDetails(val id: Int) : UmaNavigables()
}

enum class NavigationBarNavigables(
    val route: UmaNavigables,
    val label: String,
    //TODO: Add icon
    @DrawableRes
    val icon: Int // drawable

) {
    CharacterList(
        UmaNavigables.CharacterList,
        "Characters",
        //Icons taken from flaticon.com
        R.drawable.black_head_horse_side_view_with_horsehair
    ),
    SupportCards(UmaNavigables.SupportCards, "Support Cards", R.drawable.support_card)
}

//TODO: Add a top bar and buttons to navigate to different screens
@Composable
fun UmaApp(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = {
            BottomBar(onTabSelected = {
                navController.navigate(it.route)
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
        startDestination = UmaNavigables.CharacterList,
        modifier = Modifier.padding(
            top = values.calculateTopPadding(),
            bottom = values.calculateBottomPadding()
        )
    ) {
        composable<UmaNavigables.CharacterList> {
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
@Composable
fun BottomBar(onTabSelected: (NavigationBarNavigables) -> Unit) {
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(NavigationBarNavigables.CharacterList.ordinal) }

    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
        NavigationBarNavigables.entries.forEachIndexed { index, tab ->
            NavigationBarItem(
                onClick = {
                    onTabSelected(tab)
                    selectedTabIndex = index
                },
                selected = index == selectedTabIndex,
                icon = {
                    Image(
                        painter = painterResource(tab.icon),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(tab.label) }
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