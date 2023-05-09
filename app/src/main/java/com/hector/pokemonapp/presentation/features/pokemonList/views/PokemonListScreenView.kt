package com.hector.pokemonapp.presentation.features.pokemonList.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.hector.pokemonapp.presentation.LocalNavigator
import com.hector.pokemonapp.presentation.common.navigation.Navigator
import com.hector.pokemonapp.presentation.common.views.PokemonItemPlaceholderView
import com.hector.pokemonapp.presentation.common.views.ScreenScafold
import com.hector.pokemonapp.presentation.features.pokemonList.PokemonListScreenState
import com.hector.pokemonapp.presentation.features.pokemonList.PokemonListScreenViewModel
import com.hector.pokemonapp.presentation.features.pokemonList.PokemonViewItem
import com.hector.pokemonapp.presentation.features.pokemonsDetails.PokemonDetailsScreenDestination

@Composable
fun PokemonListScreenView(
    viewModel: PokemonListScreenViewModel,
    navigator: Navigator = LocalNavigator.current,
) {
    val pagingItems: LazyPagingItems<PokemonViewItem> =
        viewModel.pagingFlowState.collectAsLazyPagingItems()
    ScreenScafold {
        when (viewModel.screenState) {
            PokemonListScreenState.Error -> ErrorView()
            PokemonListScreenState.Loading -> LoadingView()
            is PokemonListScreenState.Success -> ContentView(
                pagingItems = pagingItems,
                navigator = navigator,
            )
        }
    }
}

@Composable
private fun ErrorView() {
}

@Composable
private fun LoadingView() {
    LazyColumn {
        items(10) {
            PokemonItemPlaceholderView()
        }
    }
}

@Composable
private fun ContentView(
    pagingItems: LazyPagingItems<PokemonViewItem>,
    navigator: Navigator,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        items(
            items = pagingItems,
            key = { it.nameId },
        ) { pokemon ->
            if (pokemon != null) {
                with(pokemon) {
                    PokemonItemView(
                        name = name,
                        imageUrl = imageUrl,
                        onClick = {
                            navigator.navigateToDestination(
                                destination = PokemonDetailsScreenDestination(name = nameId),
                            )
                        },
                    )
                }
            } else {
                PokemonItemPlaceholderView()
            }
        }
    }
}
