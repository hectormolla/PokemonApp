package com.hector.pokemonapp.presentation.features.pokemonList

import androidx.paging.PagingSource
import androidx.paging.PagingState

class PokemonListPagingSource(
    private val loadPage: suspend (page: Int) -> Unit,
    private val getState: () -> PokemonListScreenState,
) : PagingSource<Int, PokemonViewItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonViewItem> {
        return try {
            val page = params.key ?: 0
            loadPage(page)
            val state = getState()
            if (state is PokemonListScreenState.Success) {
                val totalCount = state.totalCount
                val pageSize = state.pageSize
                val listSize = (state.page + 1) * pageSize
                LoadResult.Page(
                    data = state.pokemons,
                    prevKey = if (page == 0) null else page.minus(1),
                    nextKey = if (page * pageSize < totalCount) page.plus(1) else null,
                    itemsBefore = getBeforeCount(pageSize, page),
                    itemsAfter = getAfterCount(listSize, totalCount),
                )
            } else {
                LoadResult.Error(IllegalStateException("screen state not ready")) }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun getBeforeCount(pageSize: Int, page: Int): Int = page * pageSize

    private fun getAfterCount(listSize: Int, totalCount: Int): Int = totalCount - listSize

    override fun getRefreshKey(state: PagingState<Int, PokemonViewItem>): Int? =
        state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
}
