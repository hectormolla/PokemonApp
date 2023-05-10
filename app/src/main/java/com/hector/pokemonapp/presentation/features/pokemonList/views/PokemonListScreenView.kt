package com.hector.pokemonapp.presentation.features.pokemonList.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.hector.pokemonapp.R
import com.hector.pokemonapp.presentation.LocalNavigator
import com.hector.pokemonapp.presentation.common.navigation.Navigator
import com.hector.pokemonapp.presentation.common.views.PokemonItemPlaceholderView
import com.hector.pokemonapp.presentation.common.views.ScreenScafold
import com.hector.pokemonapp.presentation.features.pokemonList.PokemonListScreenState
import com.hector.pokemonapp.presentation.features.pokemonList.PokemonListScreenViewModel
import com.hector.pokemonapp.presentation.features.pokemonList.PokemonViewItem
import com.hector.pokemonapp.presentation.features.pokemonsDetails.PokemonDetailsScreenDestination
import com.hector.pokemonapp.presentation.theme.errorColor
import com.hector.pokemonapp.presentation.theme.primaryColor
import com.hector.pokemonapp.presentation.theme.primaryVariantColor
import com.hector.pokemonapp.presentation.theme.regularText
import com.hector.pokemonapp.presentation.theme.regularTextBold
import com.hector.pokemonapp.presentation.theme.secondaryColor
import com.hector.pokemonapp.presentation.theme.textColor

@Composable
fun PokemonListScreenView(
    viewModel: PokemonListScreenViewModel,
    navigator: Navigator = LocalNavigator.current,
) {
    val pagingItems: LazyPagingItems<PokemonViewItem> =
        viewModel.pagingFlowState.collectAsLazyPagingItems()
    ScreenScafold {
        when (val screenState = viewModel.screenState) {
            is PokemonListScreenState.Error -> ErrorView(
                errorMessage = screenState.message,
                onReloadClick = {
                    viewModel.reload()
                    pagingItems.refresh()
                }
            )
            PokemonListScreenState.Loading -> LoadingView()
            is PokemonListScreenState.Success -> ContentView(
                pagingItems = pagingItems,
                navigator = navigator,
            )
        }
    }
}

@Composable
private fun ErrorView(
    errorMessage: String,
    onReloadClick: () -> Unit,
) {
    val lottieComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error_anim))
    val progress by animateLottieCompositionAsState(lottieComposition)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LottieAnimation(
            modifier = Modifier.size(300.dp),
            composition = lottieComposition,
            progress = { progress },
        )
        Spacer(modifier = Modifier.weight(1f))
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            elevation = 0.dp,
            backgroundColor = errorColor,
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = errorMessage,
                style = regularText,
                color = Color.White,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = primaryVariantColor),
            onClick = { onReloadClick() },
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = stringResource(R.string.reload),
                style = regularTextBold,
                color = Color.White,
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
