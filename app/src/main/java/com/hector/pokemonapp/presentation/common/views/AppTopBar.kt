package com.hector.pokemonapp.presentation.common.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import com.hector.pokemonapp.R
import com.hector.pokemonapp.presentation.LocalNavigator
import com.hector.pokemonapp.presentation.common.navigation.Navigator
import com.hector.pokemonapp.presentation.common.tags.ViewTags.BACK_BUTTON

@Composable
fun AppTopBar(
    navigator: Navigator = LocalNavigator.current,
) = TopAppBar(
    backgroundColor = Color.Transparent,
    elevation = 0.dp,
    modifier = Modifier.fillMaxWidth(),
) {
    Box(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
    ) {
        if (!navigator.isFirstScreen()) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .semantics { testTag = BACK_BUTTON },
                onClick = { navigator.goBack() },
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Go Back",
                    tint = MaterialTheme.colors.primary,
                )
            }
        }
        Image(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(R.drawable.pokemon_logo),
            contentDescription = null,
        )
    }
}
