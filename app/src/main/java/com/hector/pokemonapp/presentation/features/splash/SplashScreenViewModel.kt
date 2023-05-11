package com.hector.pokemonapp.presentation.features.splash

import androidx.lifecycle.ViewModel
import com.hector.pokemonapp.presentation.common.navigation.Navigator
import com.hector.pokemonapp.presentation.features.pokemonList.PokemonListScreenDestination

class SplashScreenViewModel : ViewModel() {
    fun splashFinished(navigator: Navigator) {
        navigator.navigateToDestination(destination = PokemonListScreenDestination) {
            popUpTo(0) { inclusive = true }
        }
    }
}
