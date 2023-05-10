package com.hector.pokemonapp.presentation.features.pokemonsDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hector.pokemonapp.common.extensions.capitalize
import com.hector.pokemonapp.common.extensions.summarize
import com.hector.pokemonapp.common.result.AppResult
import com.hector.pokemonapp.domain.entities.Pokemon
import com.hector.pokemonapp.domain.usecase.GetPokemonDetailUseCase
import kotlinx.coroutines.launch

class PokemonDetailsScreenViewModel(
    private val name: String,
    private val getPokemonsDetails: GetPokemonDetailUseCase,
) : ViewModel() {
    var screenState: PokemonDetailsScreenState? by mutableStateOf(null)
        private set

    init {
        loadDetails()
    }

    fun loadDetails() = viewModelScope.launch {
        screenState = when (val result = getPokemonsDetails(name = name)) {
            is AppResult.Error -> PokemonDetailsScreenState.Error(
                messageResId = result.exception.resMessageId,
            )
            is AppResult.Success -> PokemonDetailsScreenState.Success(
                pokemonDetailsViewItem = result.data.toPokemonDetailsViewItem(),
            )
        }
    }
}

private fun Pokemon.toPokemonDetailsViewItem() = PokemonDetailsViewItem(
    name = name.capitalize(),
    height = height,
    weight = weight,
    imageUrl = imageUrl,
    types = types.summarize(),
)
