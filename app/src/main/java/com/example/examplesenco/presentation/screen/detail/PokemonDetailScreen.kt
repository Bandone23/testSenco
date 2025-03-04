package com.example.examplesenco.presentation.screen.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.examplesenco.presentation.viewmodel.detail.PokemonDetailState
import com.example.examplesenco.presentation.viewmodel.detail.PokemonDetailViewModel
import com.example.examplesenco.utils.sharePokemon

@Composable
fun PokemonDetailScreen(viewModel: PokemonDetailViewModel, pokemonName: String, onBackClick: () -> Unit) {
    val state by viewModel.pokemonDetailState.collectAsStateWithLifecycle()
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchPokemonDetails(pokemonName)
    }

    when (state) {
        is PokemonDetailState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is PokemonDetailState.Success -> {
            val pokemon = (state as PokemonDetailState.Success).data
            val type = (state as PokemonDetailState.Success).type
            val images = (state as PokemonDetailState.Success).images
            val description = (state as PokemonDetailState.Success).description
            PokemonDetailContent(
                pokemon = pokemon,
                type = type,
                images = images,
                description = description,
                isFavorite = isFavorite, // ✅ Ahora pasamos isFavorite a la UI
                onShareClick = { sharePokemon(context, pokemon.name, images.firstOrNull()) },
                onFavoriteClick = { viewModel.toggleFavorite(pokemon.name) }, // ✅ Cambiar estado de favoritos
                onBackClick = onBackClick
            )
        }

        is PokemonDetailState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: ${(state as PokemonDetailState.Error).message}",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}