package com.hector.pokemonapp

import com.hector.pokemonapp.PokemonListScreenViewModelTest.TestData.offlineError
import com.hector.pokemonapp.PokemonListScreenViewModelTest.TestData.page0
import com.hector.pokemonapp.PokemonListScreenViewModelTest.TestData.page1
import com.hector.pokemonapp.PokemonListScreenViewModelTest.TestData.serverError
import com.hector.pokemonapp.PokemonListScreenViewModelTest.TestData.toExpectedScreenSuccessState
import com.hector.pokemonapp.common.BaseTest
import com.hector.pokemonapp.common.PokemonBuilder
import com.hector.pokemonapp.common.exception.AppError
import com.hector.pokemonapp.domain.entities.PaginatedPokemons
import com.hector.pokemonapp.domain.usecase.GetPokemonPaginatedListUseCase
import com.hector.pokemonapp.presentation.features.pokemonList.PokemonListScreenState
import com.hector.pokemonapp.presentation.features.pokemonList.PokemonListScreenViewModel
import com.hector.pokemonapp.presentation.features.pokemonList.toPokemonViewItemList
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonListScreenViewModelTest : BaseTest() {
    private lateinit var viewModel: PokemonListScreenViewModel

    private lateinit var getPokemonPaginatedListUseCase: GetPokemonPaginatedListUseCase

    @Before
    fun setUp() {
        getPokemonPaginatedListUseCase = mockk(relaxed = true)
        viewModel = PokemonListScreenViewModel(getPokemonPaginatedListUseCase)
    }

    @Test
    fun `On viewModel init, screen state is loading`() = runTest {
        val actual = viewModel.screenState

        assertEquals(PokemonListScreenState.Loading, actual)
    }

    @Test
    fun `On loadPage(), page is requested to UseCase`() = runTest {
        val pageToLoad = 2
        viewModel.loadPage(pageToLoad)

        val page = slot<Int>()
        verify { getPokemonPaginatedListUseCase.invoke(capture(page)) }
        assertEquals(pageToLoad, page.captured)
    }

    @Test
    fun `On loadPage(), screen state is published with page content`() = runTest {
        val pageToLoad = 1
        every { getPokemonPaginatedListUseCase.invoke(1) } returns flow { emit(page1) }
        viewModel.loadPage(pageToLoad)

        val actual = viewModel.screenState

        val expected = page1.toExpectedScreenSuccessState()
        assertEquals(expected, actual)
    }

    @Test
    fun `On loadPage(), error screen state is published when device is offline`() = runTest {
        every { getPokemonPaginatedListUseCase.invoke(any()) } returns flow { throw offlineError }
        viewModel.loadPage(page = 1)

        val actual = viewModel.screenState

        val expected = PokemonListScreenState.Error(messageResId = offlineError.messageResId)
        assertEquals(expected, actual)
    }

    @Test
    fun `On loadPage(), error screen state is published when server fails`() = runTest {
        every { getPokemonPaginatedListUseCase.invoke(any()) } returns flow { throw serverError }
        viewModel.loadPage(page = 1)

        val actual = viewModel.screenState

        val expected = PokemonListScreenState.Error(messageResId = serverError.messageResId)
        assertEquals(expected, actual)
    }

    object TestData {
        private val pokemon1 = PokemonBuilder().id(1).build()
        private val pokemon2 = PokemonBuilder().id(2).build()

        val page0 = PaginatedPokemons(
            count = 10,
            page = 0,
            pageSize = 2,
            pokemons = listOf(pokemon1, pokemon2),
        )

        val page1 = PaginatedPokemons(
            count = 10,
            page = 1,
            pageSize = 2,
            pokemons = listOf(pokemon1, pokemon2),
        )

        fun PaginatedPokemons.toExpectedScreenSuccessState() = PokemonListScreenState.Success(
            totalCount = count,
            page = page,
            pageSize = pageSize,
            pokemons = pokemons.toPokemonViewItemList(),
        )

        val offlineError = AppError.Offline(exception = Exception())
        val serverError = AppError.Server(exception = Exception())
    }
}
