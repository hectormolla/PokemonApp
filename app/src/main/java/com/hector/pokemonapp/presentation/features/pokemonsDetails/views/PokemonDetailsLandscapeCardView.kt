package com.hector.pokemonapp.presentation.features.pokemonsDetails.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.hector.pokemonapp.R
import com.hector.pokemonapp.presentation.features.pokemonsDetails.PokemonDetailsViewItem
import com.hector.pokemonapp.presentation.theme.AppColors
import com.hector.pokemonapp.presentation.theme.AppTypography

@Composable
fun PokemonDetailsLandscapeCardView(
    details: PokemonDetailsViewItem,
    pictureSize: Dp = 150.dp,
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        BodyView(
            details = details,
            pictureSize = pictureSize,
        )
        HeaderView(
            imageUrl = details.imageUrl,
            pictureSize = pictureSize,
        )
    }
}

@Composable
private fun HeaderView(
    imageUrl: String,
    pictureSize: Dp,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxHeight(),
    ) {
        Card(
            shape = CircleShape,
            elevation = 8.dp,
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(pictureSize)
                    .clip(CircleShape)
                    .padding(15.dp),
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(imageUrl)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                placeholder = painterResource(id = R.drawable.pokemon_logo),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun BodyView(
    details: PokemonDetailsViewItem,
    pictureSize: Dp,
) {
    Card(
        modifier = Modifier
            .padding(start = pictureSize / 2)
            .fillMaxHeight(),
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
    ) {
        with(details) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 16.dp)
                    .padding(start = (pictureSize / 2) + 16.dp, end = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = name,
                    color = AppColors.primary,
                    style = AppTypography.h1,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    InfoItemView(
                        modifier = Modifier.weight(1f),
                        title = stringResource(R.string.weight),
                        subtitle = "$weight",
                    )
                    InfoItemView(
                        modifier = Modifier.weight(1f),
                        title = stringResource(R.string.height),
                        subtitle = "$height",
                    )
                }
                InfoItemView(
                    title = stringResource(R.string.types),
                    subtitle = types,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonDetailsLandscapeCardPreview() {
    val details = PokemonDetailsViewItem(
        name = "Bulbasaur",
        height = 10,
        weight = 10,
        imageUrl = "",
        types = "fire and water",
    )
    PokemonDetailsLandscapeCardView(
        details = details,
    )
}
