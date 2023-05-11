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
import com.hector.pokemonapp.presentation.common.navigation.Navigator
import com.hector.pokemonapp.presentation.features.pokemonsDetails.PokemonDetailsScreenDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch

class PokemonListScreenViewModel(
    private val getPokemonPaginatedList: GetPokemonPaginatedListUseCase,
) : ViewModel() {
    var screenState: PokemonListScreenState by mutableStateOf(PokemonListScreenState.Loading)
        private set
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    val pagingFlowState = Pager(PagingConfig(enablePlaceholders = true, pageSize = PAGE_SIZE)) {
        PokemonListPagingSource(
            loadPage = { page -> loadPage(page) },
            getState = { screenState },
        )
    }.flow.cachedIn(viewModelScope)

    fun loading() {
        screenState = PokemonListScreenState.Loading
    }

    suspend fun loadPage(page: Int) {
        getPokemonPaginatedList(page = page)
            .catch {
                processError(error = it)
            }
            .collect {
                processSuccess(pokemonsPage = it)
                _isRefreshing.emit(false)
            }
    }

    fun showDetails(navigator: Navigator, nameId: String) {
        navigator.navigateToDestination(
            destination = PokemonDetailsScreenDestination(name = nameId),
        )
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
