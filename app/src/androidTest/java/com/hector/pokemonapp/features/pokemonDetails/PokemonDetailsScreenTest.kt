package com.hector.pokemonapp.features.pokemonDetails

import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavOptionsBuilder
import com.hector.pokemonapp.common.exception.AppError
import com.hector.pokemonapp.common.extensions.summarize
import com.hector.pokemonapp.common.result.AppResult
import com.hector.pokemonapp.domain.entities.Pokemon
import com.hector.pokemonapp.domain.usecase.GetPokemonDetailUseCase
import com.hector.pokemonapp.features.pokemonDetails.PokemonDetailsScreenTest.TestData.pokemon
import com.hector.pokemonapp.presentation.LocalNavigator
import com.hector.pokemonapp.presentation.MainActivity
import com.hector.pokemonapp.presentation.common.navigation.Destination
import com.hector.pokemonapp.presentation.common.navigation.Navigator
import com.hector.pokemonapp.presentation.common.tags.ViewTags
import com.hector.pokemonapp.presentation.features.pokemonsDetails.PokemonDetailsScreenViewModel
import com.hector.pokemonapp.presentation.features.pokemonsDetails.views.PokemonDetailsScreenView
import com.hector.pokemonapp.presentation.theme.PokemonTheme
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel

class PokemonDetailsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var getPokemonDetailUseCase: TestData.GetPokemonDetailUseCaseFake
    private lateinit var navigator: TestData.NavigatorFake

    @Before
    fun setUp() {
        getPokemonDetailUseCase = TestData.GetPokemonDetailUseCaseFake()
        navigator = TestData.NavigatorFake()
    }

    @Test
    fun onPokemonLoadSuccess_PokemonDataIsProperlyDisplayed() {
        renderScreen()
        composeTestRule.onNodeWithText(pokemon.name).assertIsDisplayed()
        composeTestRule.onNodeWithText("${pokemon.weight}").assertIsDisplayed()
        composeTestRule.onNodeWithText("${pokemon.height}").assertIsDisplayed()
        composeTestRule.onNodeWithText(pokemon.types.summarize()).assertIsDisplayed()
    }

    @Test
    fun onPokemonLoadSuccess_BackButtonIsDisplayedAndClickable() {
        renderScreen()
        composeTestRule.onNodeWithTag(ViewTags.BACK_BUTTON).assertIsDisplayed()
        composeTestRule.onNodeWithTag(ViewTags.BACK_BUTTON).performClick()
        val actual = navigator.backTimesClicked
        assertEquals(1, actual)
    }

    @Test
    fun onPokemonLoadWithUnknownError_ErrorViewDisplayed() {
        val error = AppError.Unknown(exception = Exception())
        val errorResult = AppResult.Error(value = error)
        renderScreen(result = errorResult)
        composeTestRule.onNodeWithText("An unexpected error occurred. Please, try again later").assertIsDisplayed()
    }

    @Test
    fun onPokemonLoadWithOfflineError_ErrorViewDisplayed() {
        val error = AppError.Offline(exception = Exception())
        val errorResult = AppResult.Error(value = error)
        renderScreen(result = errorResult)
        composeTestRule.onNodeWithText("Your device Is offline. Please, check your connection and try again.").assertIsDisplayed()
    }

    private fun renderScreen(result: AppResult<Pokemon> = AppResult.Success(data = pokemon)) {
        getPokemonDetailUseCase.setResult(result = result)
        composeTestRule.activity.setContent {
            CompositionLocalProvider(LocalNavigator provides navigator) {
                PokemonTheme {
                    val viewModel = PokemonDetailsScreenViewModel(
                        name = pokemon.name,
                        getPokemonsDetails = getPokemonDetailUseCase,
                    )
                    PokemonDetailsScreenView(viewModel = viewModel)
                }
            }
        }
    }

    object TestData {
        class GetPokemonDetailUseCaseFake : GetPokemonDetailUseCase {
            private var result: AppResult<Pokemon> = AppResult.Success(data = pokemon)

            fun setResult(result: AppResult<Pokemon>) {
                this.result = result
            }

            override suspend fun invoke(name: String): AppResult<Pokemon> = result
        }

        class NavigatorFake : Navigator {
            var backTimesClicked: Int = 0
                private set

            override fun navigateToDestination(
                destination: Destination,
                options: NavOptionsBuilder.() -> Unit,
            ) { }

            override fun goBack() { backTimesClicked++ }

            override fun isFirstScreen(): Boolean = false
        }

        val pokemon = Pokemon(
            id = 1,
            name = "Bulbasaur",
            weight = 69,
            height = 7,
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/1.svg",
            types = listOf("grass", "poison"),
        )
    }
}
