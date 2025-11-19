package com.example.uma.ui.screens.umalist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import coil3.compose.AsyncImage
import com.example.uma.R
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import coil3.request.ImageRequest
import coil3.request.crossfade

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
        items(items = umaCharacters, key = { it.id }) { character ->
            //TODO: Add onclick
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable(enabled = true, onClick = {})
                    .height(200.dp)
                    .padding(4.dp)
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription = null,
                    modifier = Modifier.height(150.dp),
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
            UmaCharacter(1, "Special Week", ""),
            UmaCharacter(2, "Tokai Teio", ""),
            UmaCharacter(3, "Silence Suzuka", ""),
        )
    UmaColumn(umaList)
}