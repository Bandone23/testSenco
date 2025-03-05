package com.example.examplesenco.presentation.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.examplesenco.presentation.viewmodel.detail.PokemonDetailState
import com.example.examplesenco.presentation.viewmodel.detail.PokemonDetailViewModel
import com.example.examplesenco.utils.sharePokemon

@Composable
fun PokemonDetailScreen(viewModel: PokemonDetailViewModel, pokemonName: String, onBackClick: () -> Unit) {
    val state by viewModel.pokemonDetailState.collectAsStateWithLifecycle()
    val isFavorite by viewModel.isFavorite.collectAsStateWithLifecycle()
    val context = LocalContext.current


    LaunchedEffect(pokemonName) {
        viewModel.fetchPokemonDetails(pokemonName)
    }

    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                when (val currentState = state) {
                    PokemonDetailState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    is PokemonDetailState.Success -> {
                        PokemonDetailContent(
                            pokemon = currentState.data,
                            type = currentState.type,
                            images = currentState.images,
                            description = currentState.description!!,
                            isFavorite = isFavorite,
                            onShareClick = {
                                sharePokemon(
                                    context,
                                    currentState.data.name,
                                    currentState.images.firstOrNull()
                                )
                            },
                            onFavoriteClick = {
                                viewModel.toggleFavorite(currentState.data.name)
                            },
                            onBackClick = onBackClick
                        )
                    }

                    is PokemonDetailState.Error -> {
                        ErrorContent(
                            message = currentState.message,
                            onRetry = { viewModel.fetchPokemonDetails(pokemonName) }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Error: $message",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Reintentar")
        }
    }
}