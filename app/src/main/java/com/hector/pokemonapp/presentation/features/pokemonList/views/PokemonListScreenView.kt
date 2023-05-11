package com.hector.pokemonapp.presentation.features.pokemonList.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.hector.pokemonapp.presentation.LocalNavigator
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
    val navigator = LocalNavigator.current
    val pagingItems: LazyPagingItems<PokemonViewItem> =
        viewModel.pagingFlowState.collectAsLazyPagingItems()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    ScreenScafold {
        when (val screenState = viewModel.screenState) {
            is PokemonListScreenState.Error -> ErrorView(
                errorMessage = stringResource(id = screenState.messageResId),
                onReloadClick = {
                    viewModel.loading()
                    pagingItems.refresh()
                },
            )
            PokemonListScreenState.Loading -> LoadingView()
            is PokemonListScreenState.Success -> ContentView(
                pagingItems = pagingItems,
                onItemClick = { nameId ->
                    viewModel.showDetails(navigator = navigator, nameId = nameId)
                },
                isRefreshing = isRefreshing,
                onRefreshing = {
                    viewModel.loading()
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ContentView(
    pagingItems: LazyPagingItems<PokemonViewItem>,
    onItemClick: (String) -> Unit,
    isRefreshing: Boolean,
    onRefreshing: () -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            onRefreshing()
            pagingItems.refresh()
        },
    )
    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
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
        PullRefreshIndicator(
            modifier = Modifier.align(Alignment.TopCenter),
            refreshing = isRefreshing,
            state = pullRefreshState,
        )
    }
}
