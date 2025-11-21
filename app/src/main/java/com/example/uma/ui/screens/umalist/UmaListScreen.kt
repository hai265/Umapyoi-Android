package com.example.uma.ui.screens.umalist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.uma.R
import com.example.uma.ui.models.UmaCharacter

@Composable
fun UmaListScreen(modifier: Modifier = Modifier, onTapCharacter: (Int) -> Unit) {
    val viewModel: UmaListViewModel = hiltViewModel()
    val umaListState by viewModel.umaList.collectAsState()

    UmaColumn(
        umaCharacters = umaListState.umaList,
        onTapCharacter = onTapCharacter,
        modifier = modifier
    )
}

@Composable
fun UmaColumn(
    umaCharacters: List<UmaCharacter>,
    onTapCharacter: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(columns = GridCells.Fixed(3), modifier) {
        items(items = umaCharacters, key = { it.id }) { character ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable(enabled = true, onClick = { onTapCharacter(character.id) })
                    .height(200.dp)
                    .padding(4.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(character.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.height(150.dp),
                    //TODO: special week icon ONLY FOR PREVIEW
                    placeholder = painterResource(R.drawable.specialweek_icon),
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
    UmaColumn(umaList, onTapCharacter = {})
}