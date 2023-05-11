package com.hector.pokemonapp

import com.hector.pokemonapp.PokemonDetailsScreenViewModelTest.TestData.expectedPokemonDetails
import com.hector.pokemonapp.PokemonDetailsScreenViewModelTest.TestData.offlineErrorResult
import com.hector.pokemonapp.PokemonDetailsScreenViewModelTest.TestData.pokemon
import com.hector.pokemonapp.PokemonDetailsScreenViewModelTest.TestData.serverErrorResult
import com.hector.pokemonapp.PokemonDetailsScreenViewModelTest.TestData.successResult
import com.hector.pokemonapp.common.BaseTest
import com.hector.pokemonapp.common.exception.AppError
import com.hector.pokemonapp.common.extensions.capitalize
import com.hector.pokemonapp.common.extensions.summarize
import com.hector.pokemonapp.common.result.AppResult
import com.hector.pokemonapp.domain.entities.Pokemon
import com.hector.pokemonapp.domain.usecase.GetPokemonDetailUseCase
import com.hector.pokemonapp.presentation.features.pokemonsDetails.PokemonDetailsScreenState
import com.hector.pokemonapp.presentation.features.pokemonsDetails.PokemonDetailsScreenViewModel
import com.hector.pokemonapp.presentation.features.pokemonsDetails.PokemonDetailsViewItem
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonDetailsScreenViewModelTest: BaseTest() {
    private lateinit var viewModel: PokemonDetailsScreenViewModel
    private lateinit var getPokemonDetailUseCase: GetPokemonDetailUseCase

    @Before
    fun setUp() {
        getPokemonDetailUseCase = mockk(relaxed = true)
        viewModel = PokemonDetailsScreenViewModel(pokemon.name,getPokemonDetailUseCase)
    }

    @Test
    fun `On ViewModel init, details are requested for name once`() = runTest {
        advanceUntilIdle()

        val nameRequested = slot<String>()
        coVerify { getPokemonDetailUseCase(capture(nameRequested)) }
        coVerify(exactly = 1) { getPokemonDetailUseCase(pokemon.name) }
        assertEquals(pokemon.name, nameRequested.captured)
    }

    @Test
    fun `On loadDetails() called after viewModel init, details are requested twice`() = runTest {
        viewModel.loadDetails()
        advanceUntilIdle()

        coVerify(exactly = 2) { getPokemonDetailUseCase(any()) }
    }

    @Test
    fun `On loadDetails(), details are returned successfully`() = runTest {
        coEvery { getPokemonDetailUseCase(pokemon.name) } returns successResult

        viewModel.loadDetails()
        advanceUntilIdle()
        val actual = viewModel.screenState

        val expectedScreenState = PokemonDetailsScreenState.Success(pokemonDetailsViewItem = expectedPokemonDetails)
        assertEquals(expectedScreenState, actual)
    }

    @Test
    fun `On loadDetails() with offline device, error is returned`() = runTest {
        coEvery { getPokemonDetailUseCase(any()) } returns offlineErrorResult

        viewModel.loadDetails()
        advanceUntilIdle()
        val actual = viewModel.screenState

        val expectedScreenState = PokemonDetailsScreenState.Error(
            messageResId =  offlineErrorResult.value.messageResId,
        )
        assertEquals(expectedScreenState, actual)
    }

    @Test
    fun `On loadDetails() with server fail, error is returned`() = runTest {
        coEvery { getPokemonDetailUseCase(any()) } returns serverErrorResult

        viewModel.loadDetails()
        advanceUntilIdle()
        val actual = viewModel.screenState

        val expectedScreenState = PokemonDetailsScreenState.Error(
            messageResId =  serverErrorResult.value.messageResId,
        )
        assertEquals(expectedScreenState, actual)
    }

    object TestData {
        val pokemon = Pokemon(
            id = 1,
            name = "Bulbasaur",
            weight = 69,
            height = 7,
            imageUrl = "",
            types = listOf("grass", "poison"),
        )

        val expectedPokemonDetails = PokemonDetailsViewItem(
            name = pokemon.name.capitalize(),
            height = pokemon.height,
            weight = pokemon.weight,
            imageUrl = pokemon.imageUrl,
            types = pokemon.types.summarize(),
        )

        val successResult = AppResult.Success(data = pokemon)
        val offlineErrorResult = AppResult.Error(value = AppError.Offline(exception = Exception()))
        val serverErrorResult = AppResult.Error(value = AppError.Server(exception = Exception()))
    }
}