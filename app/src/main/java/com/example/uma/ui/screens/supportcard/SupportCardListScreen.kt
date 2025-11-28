package com.example.uma.ui.screens.supportcard

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.uma.ui.screens.umalist.UmaColumn
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun SupportCardListScreen(modifier: Modifier, onTapSupportCard: (Int) -> Unit) {
    val viewModel: SupportCardListViewModel = hiltViewModel()
    val umaListState by viewModel.supportCardList.collectAsState()


}