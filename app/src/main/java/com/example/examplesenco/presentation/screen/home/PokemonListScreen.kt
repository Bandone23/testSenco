package com.example.examplesenco.presentation.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.examplesenco.presentation.screen.shimmer.ShimmerPokemonList
import com.example.examplesenco.presentation.viewmodel.home.PokemonViewModel
import com.example.examplesenco.presentation.viewmodel.home.PokemonViewState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(
    viewModel: PokemonViewModel,
    modifier: Modifier = Modifier,
    onPokemonClick: (String) -> Unit,
    onFavoritesClick: () -> Unit
) {
    val pokemonState by viewModel.pokemonState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.fetchPokemonList()  // Llama la función para obtener los pokemones
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Pokémon") },
                actions = {
                    IconButton(onClick = { onFavoritesClick() }) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favoritos")
                    }
                }
            )
        },
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
    when (pokemonState) {
        is PokemonViewState.Loading -> {
            ShimmerPokemonList(modifier)
        }

        is PokemonViewState.Success -> {
            val pokemonFlow = (pokemonState as PokemonViewState.Success).data
            val pokemonPagingData = pokemonFlow.collectAsLazyPagingItems()

            if (pokemonPagingData.itemCount == 0) {
                LoadingScreen()
            } else {
                PokemonList(pokemonPagingData, onPokemonClick)
            }
        }

        is PokemonViewState.Error -> {
            val errorMessage = (pokemonState as PokemonViewState.Error).message
            LaunchedEffect(errorMessage) {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = errorMessage,
                        duration = SnackbarDuration.Short
                    )
                }
            }
            ErrorScreen(errorMessage, modifier = modifier)
        }
    }
        }
    }
}