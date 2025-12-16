package com.example.uma.ui.screens.character.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil3.load
import coil3.request.crossfade
import com.example.uma.R
import com.example.uma.databinding.FragmentCharacterDetailsBinding
import com.example.uma.ui.screens.character.CharacterDetailsScreenViewModel
import com.example.uma.ui.screens.character.CharacterScreenUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailsScreenFragmentViewBinding : Fragment(R.layout.fragment_character_details) {
    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    private val viewModel: CharacterDetailsScreenViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { uiState ->
                when (uiState) {
                    is CharacterScreenUiState.Error -> {
                        binding.errorLoadingText.text = "Error loading character details"
                    }

                    CharacterScreenUiState.Loading -> {
                        binding.errorLoadingText.text = "Loading"
                    }

                    is CharacterScreenUiState.Success -> {
                        binding.apply {
                            errorLoadingText.visibility = View.INVISIBLE
                            val character = uiState.characterDetailed
                            characterName.text = character.characterBasic.name
                            slogan.text = character.characterProfile.slogan
                            characterImage.load(character.characterBasic.image) {
                                crossfade(true)
                            }
                        }
                    }
                }
            }
        }
    }
}