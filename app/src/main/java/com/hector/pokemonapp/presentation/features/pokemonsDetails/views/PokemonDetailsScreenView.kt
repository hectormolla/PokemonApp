package com.hector.pokemonapp.presentation.features.pokemonsDetails.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hector.pokemonapp.presentation.common.animations.linearAnimationSpec
import com.hector.pokemonapp.presentation.common.views.ScreenScafold
import com.hector.pokemonapp.presentation.features.pokemonsDetails.PokemonDetailsScreenState
import com.hector.pokemonapp.presentation.features.pokemonsDetails.PokemonDetailsScreenViewModel

@Composable
fun PokemonDetailsScreenView(
    viewModel: PokemonDetailsScreenViewModel,
) = PokemonDetailsScreenContentView(
    screenState = viewModel.screenState,
)

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun PokemonDetailsScreenContentView(
    screenState: PokemonDetailsScreenState?,
) {
    ScreenScafold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            contentAlignment = Alignment.Center,
        ) {
            AnimatedVisibility(
                visible = screenState != null,
                enter = scaleIn(animationSpec = linearAnimationSpec(durationInMillis = 250)),
            ) {
                screenState?.let {
                    PokemonDetailsCardView(details = it.pokemonDetailsViewItem)
                }
            }
        }
    }
}
