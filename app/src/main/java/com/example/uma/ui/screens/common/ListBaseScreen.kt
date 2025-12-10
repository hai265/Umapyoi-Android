package com.example.uma.ui.screens.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.uma.ui.screens.umalist.sorting.SearchTextField

@Composable
fun ScreenWithSearchBar(
    textFieldState: TextFieldState,
//  We need to know if content is empty so we render a blank view for the pull down
    contentEmpty: Boolean,
    onRefresh: () -> Unit,
    syncing: Boolean,
    modifier: Modifier = Modifier,
    searchBoxLabel: String,
    content: @Composable () -> Unit,
    ) {
    Column(modifier = modifier.fillMaxSize()) {
        SearchTextField(
            textFieldState,
            searchBoxLabel,
            modifier = Modifier.fillMaxWidth()
        )
        PullToRefreshBox(
            isRefreshing = syncing,
            onRefresh = onRefresh,
        ) {
            if (contentEmpty) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item { Box(Modifier
                        .fillMaxWidth()
                        .height(500.dp)) }
                }
            } else {
                content()
            }
        }
    }

}