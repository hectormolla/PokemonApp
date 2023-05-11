package com.hector.pokemonapp.presentation.features.pokemonList.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.hector.pokemonapp.R
import com.hector.pokemonapp.presentation.theme.AppColors
import com.hector.pokemonapp.presentation.theme.AppTypography

@Composable
fun PokemonItemView(
    name: String,
    imageUrl: String,
    onClick: () -> Unit,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(130.dp)
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable { onClick() },
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier
                    .aspectRatio(1f, true),
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(imageUrl)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                placeholder = painterResource(id = R.drawable.pokemon_logo),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = name,
                color = AppColors.primary,
                style = AppTypography.h1,
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom,
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowRight,
                    contentDescription = null,
                    tint = AppColors.primary,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonItemViewPreview() {
    PokemonItemView(
        name = "Bulbasaur",
        imageUrl = "",
        onClick = {},
    )
}
