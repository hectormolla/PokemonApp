package com.hector.pokemonapp.presentation.features.splash

import androidx.lifecycle.ViewModel
import com.hector.pokemonapp.presentation.common.navigation.Navigator
import com.hector.pokemonapp.presentation.features.pokemonList.PokemonListScreenDestination

class SplashScreenViewModel(
    private val navigator: Navigator,
) : ViewModel() {
    fun splashFinished() {
        navigator.navigateToDestination(destination = PokemonListScreenDestination) {
            popUpTo(0) { inclusive = true }
        }
    }
}
