package com.hector.pokemonapp.presentation.features.pokemonsDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hector.pokemonapp.common.extensions.capitalize
import com.hector.pokemonapp.common.extensions.summarize
import com.hector.pokemonapp.domain.entities.Pokemon
import com.hector.pokemonapp.domain.usecase.GetPokemonDetailUseCase
import kotlinx.coroutines.launch

class PokemonDetailsScreenViewModel(
    name: String,
    private val getPokemonsDetails: GetPokemonDetailUseCase,
) : ViewModel() {
    var screenState: PokemonDetailsScreenState? by mutableStateOf(null)
        private set

    init {
        viewModelScope.launch {
            val pokemon = getPokemonsDetails(name = name)
            screenState = PokemonDetailsScreenState(
                pokemonDetailsViewItem = pokemon.toPokemonDetailsViewItem(),
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
