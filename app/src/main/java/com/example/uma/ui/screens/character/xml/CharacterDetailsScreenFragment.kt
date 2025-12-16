package com.example.uma.ui.screens.character.xml

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil3.load
import coil3.request.crossfade
import com.example.uma.R
import com.example.uma.ui.screens.character.CharacterDetailsScreenViewModel
import com.example.uma.ui.screens.character.CharacterScreenUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailsScreenFragment : Fragment(R.layout.fragment_character_details) {
    private val viewModel: CharacterDetailsScreenViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterName: TextView = view.findViewById(R.id.character_name)
        val characterSlogan: TextView = view.findViewById(R.id.slogan)
        val characterImage: ImageView = view.findViewById(R.id.character_image)
        val errorLoadingText: TextView = view.findViewById(R.id.error_loading_text)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { uiState ->
                when (uiState) {
                    is CharacterScreenUiState.Error -> {
                        errorLoadingText.text = "Error loading character details"
                    }
                    CharacterScreenUiState.Loading -> {
                        errorLoadingText.text = "Loading"
                    }
                    is CharacterScreenUiState.Success -> {
                        errorLoadingText.visibility = View.INVISIBLE
                        val character = uiState.characterDetailed
                        characterName.text = character.characterBasic.name
                        characterSlogan.text = character.characterProfile.slogan
                        characterImage.load(character.characterBasic.image) {
                            crossfade(true)
                        }
                    }
                }
            }
        }
    }
}