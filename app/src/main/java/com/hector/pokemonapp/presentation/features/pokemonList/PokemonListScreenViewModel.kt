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
import com.hector.pokemonapp.R
import com.hector.pokemonapp.common.exception.AppError
import com.hector.pokemonapp.common.extensions.capitalize
import com.hector.pokemonapp.domain.entities.PaginatedPokemons
import com.hector.pokemonapp.domain.entities.Pokemon
import com.hector.pokemonapp.domain.usecase.GetPokemonPaginatedListUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

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

    fun reload() = viewModelScope.launch {
        screenState = PokemonListScreenState.Loading
        loadPage(page = 0)
    }

    suspend fun loadPage(page: Int) {
        getPokemonPaginatedList(page = page)
            .catch {
                processError(error = it)
            }
            .collect {
                processSuccess(pokemonsPage = it)
            }
    }

    private fun processSuccess(pokemonsPage: PaginatedPokemons) = with(pokemonsPage) {
        screenState = PokemonListScreenState.Success(
            totalCount = count,
            page = page,
            pageSize = pageSize,
            pokemons = pokemonsPage.pokemons.toPokemonViewItemList(),
        )
    }

    private fun processError(error: Throwable) {
        val messageResId = when (error) {
            is AppError -> error.messageResId
            else -> R.string.error_unknown
        }
        screenState = PokemonListScreenState.Error(messageResId = messageResId)
    }
}

fun List<Pokemon>.toPokemonViewItemList(): List<PokemonViewItem> = this.map {
    PokemonViewItem(
        nameId = it.name,
        name = it.name.capitalize(),
        imageUrl = it.imageUrl,
    )
}
