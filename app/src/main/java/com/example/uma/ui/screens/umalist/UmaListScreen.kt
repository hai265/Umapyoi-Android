package com.example.uma.ui.screens.umalist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.uma.ui.models.UmaCharacter
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.uma.R
import androidx.compose.runtime.getValue

@Composable
fun UmaListScreen(modifier: Modifier = Modifier) {
    val viewModel: UmaListViewModel = hiltViewModel()
    val umaListState by viewModel.umaList.collectAsState()

    UmaColumn(umaListState.umaList, modifier)
}

@Composable
fun UmaColumn(umaCharacters: List<UmaCharacter>, modifier: Modifier = Modifier) {
    //TODO: Fix lag when scrolling
    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier) {
        items(umaCharacters) { character ->
            //TODO: Add onclick
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier.clickable(enabled = true, onClick = {})
            ) {
                AsyncImage(
                    model = character.image, contentDescription = null,
                    modifier = modifier.height(150.dp),
                    //TODO: special week icon ONLY FOR PREVIEW
//                    placeholder = painterResource(R.drawable.specialweek_icon),
                )
                Text(character.name)
            }
        }
    }
}

@Preview
@Composable
fun UmaColumnPreview() {
    val umaList =
        listOf<UmaCharacter>(
            UmaCharacter("Special Week", ""),
            UmaCharacter("Tokai Teio", ""),
            UmaCharacter("Silence Suzuka", ""),
        )
    UmaColumn(umaList)
}