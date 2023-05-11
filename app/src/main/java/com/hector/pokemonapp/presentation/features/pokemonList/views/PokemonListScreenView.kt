package com.hector.pokemonapp.presentation.features.pokemonList.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.hector.pokemonapp.presentation.common.views.ErrorView
import com.hector.pokemonapp.presentation.common.views.PokemonItemPlaceholderView
import com.hector.pokemonapp.presentation.common.views.ScreenScafold
import com.hector.pokemonapp.presentation.features.pokemonList.PokemonListScreenState
import com.hector.pokemonapp.presentation.features.pokemonList.PokemonListScreenViewModel
import com.hector.pokemonapp.presentation.features.pokemonList.PokemonViewItem

@Composable
fun PokemonListScreenView(
    viewModel: PokemonListScreenViewModel,
) {
    val pagingItems: LazyPagingItems<PokemonViewItem> =
        viewModel.pagingFlowState.collectAsLazyPagingItems()
    ScreenScafold {
        when (val screenState = viewModel.screenState) {
            is PokemonListScreenState.Error -> ErrorView(
                errorMessage = stringResource(id = screenState.messageResId),
                onReloadClick = {
                    viewModel.reload()
                    pagingItems.refresh()
                },
            )
            PokemonListScreenState.Loading -> LoadingView()
            is PokemonListScreenState.Success -> ContentView(
                pagingItems = pagingItems,
                onItemClick = { nameId ->
                    viewModel.showDetails(nameId = nameId)
                },
            )
        }
    }
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
    onItemClick: (String) -> Unit,
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
                            onItemClick(nameId)
                        },
                    )
                }
            } else {
                PokemonItemPlaceholderView()
            }
        }
    }
}
