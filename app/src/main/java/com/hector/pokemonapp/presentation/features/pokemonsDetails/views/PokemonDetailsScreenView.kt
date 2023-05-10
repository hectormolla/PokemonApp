package com.hector.pokemonapp.presentation.features.pokemonsDetails.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.hector.pokemonapp.presentation.common.animations.linearAnimationSpec
import com.hector.pokemonapp.presentation.common.views.ErrorView
import com.hector.pokemonapp.presentation.common.views.ScreenScafold
import com.hector.pokemonapp.presentation.features.pokemonsDetails.PokemonDetailsScreenState
import com.hector.pokemonapp.presentation.features.pokemonsDetails.PokemonDetailsScreenViewModel
import com.hector.pokemonapp.presentation.features.pokemonsDetails.PokemonDetailsViewItem

@Composable
fun PokemonDetailsScreenView(
    viewModel: PokemonDetailsScreenViewModel,
) {
    ScreenScafold {
        when (val screenState = viewModel.screenState) {
            is PokemonDetailsScreenState.Error -> ErrorView(
                errorMessage = stringResource(id = screenState.messageResId),
                onReloadClick = {
                    viewModel.loadDetails()
                },
            )
            is PokemonDetailsScreenState.Success -> ContentView(
                pokemonDetailsViewItem = screenState.pokemonDetailsViewItem,
            )
            null -> LoadingView()
        }
    }
}

@Composable
private fun LoadingView() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        // Left empty for simplicity as is a quick request
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ContentView(
    pokemonDetailsViewItem: PokemonDetailsViewItem,
) {
    var visibility by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) {
        visibility = true
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp),
        contentAlignment = Alignment.Center,
    ) {
        AnimatedVisibility(
            visible = visibility,
            enter = scaleIn(animationSpec = linearAnimationSpec(durationInMillis = 250)),
        ) {
            PokemonDetailsCardView(details = pokemonDetailsViewItem)
        }
    }
}
