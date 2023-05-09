package com.hector.pokemonapp.presentation.features.pokemonList

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.hector.pokemonapp.common.extensions.capitalize
import com.hector.pokemonapp.domain.entities.Pokemon
import com.hector.pokemonapp.domain.usecase.GetPokemonPaginatedListUseCase
import kotlinx.coroutines.flow.catch

class PokemonListScreenViewModel(
    private val getPokemonPaginatedList: GetPokemonPaginatedListUseCase,
) : ViewModel() {
    var screenState: PokemonListScreenState by mutableStateOf(PokemonListScreenState.Loading)
        private set
    val pagingFlowState = Pager(PagingConfig(enablePlaceholders = true, pageSize = PAGE_SIZE)) {
        PokemonListPagingSource(
            loadPage = { page -> loadPage(page) },
            getState = { screenState },
        )
    }.flow.cachedIn(viewModelScope)

    private suspend fun loadPage(page: Int) {
        getPokemonPaginatedList(page = page)
            .catch {
                screenState = PokemonListScreenState.Error
            }
            .collect { pokemonsPage ->
                with(pokemonsPage) {
                    screenState = PokemonListScreenState.Success(
                        totalCount = count,
                        page = page,
                        pageSize = pageSize,
                        pokemons = pokemonsPage.pokemons.toPokemonViewItemList(),
                    )
                }
            }
    }
}

private fun List<Pokemon>.toPokemonViewItemList(): List<PokemonViewItem> = this.map {
    PokemonViewItem(
        nameId = it.name,
        name = it.name.capitalize(),
        imageUrl = it.imageUrl,
    )
}
