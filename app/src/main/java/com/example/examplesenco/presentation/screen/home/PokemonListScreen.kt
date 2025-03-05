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
import androidx.paging.LoadState
import com.example.examplesenco.presentation.screen.shimmer.ShimmerPokemonList
import com.example.examplesenco.presentation.viewmodel.home.PokemonIntent
import com.example.examplesenco.presentation.viewmodel.home.PokemonViewModel
import com.example.examplesenco.presentation.viewmodel.home.PokemonViewState
import kotlinx.coroutines.launch
import androidx.paging.compose.collectAsLazyPagingItems


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
        viewModel.processIntent(PokemonIntent.LoadPokemons)  // Llama a la acción de cargar los Pokémon
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
            when (val currentState = pokemonState) {
                is PokemonViewState.Loading -> {
                    ShimmerPokemonList(modifier)
                }

                is PokemonViewState.Success -> {
                    val pokemonFlow = currentState.data
                    val pokemonPagingData = pokemonFlow.collectAsLazyPagingItems()

                    // Manejar errores de carga de páginas
                    val loadState = pokemonPagingData.loadState

                    // Manejar error de carga inicial
                    when (loadState.refresh) {
                        is LoadState.Error -> {
                            val errorMessage = (loadState.refresh as LoadState.Error).error.message
                                ?: "Error desconocido al cargar"

                            LaunchedEffect(errorMessage) {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = errorMessage,
                                        duration = SnackbarDuration.Long
                                    )
                                }
                            }

                            ErrorScreen(errorMessage, modifier = modifier)
                        }

                        is LoadState.Loading -> {
                            ShimmerPokemonList(modifier)
                        }

                        is LoadState.NotLoading -> {
                            if (pokemonPagingData.itemCount == 0) {
                                ErrorScreen("No se encontraron Pokémon", modifier = modifier)
                            } else {
                                PokemonList(pokemonPagingData, onPokemonClick)
                            }
                        }
                    }

                    // Manejar errores de carga de páginas adicionales
                    when (loadState.append) {
                        is LoadState.Error -> {
                            val errorMessage = (loadState.append as LoadState.Error).error.message
                                ?: "Error al cargar más Pokémon"

                            LaunchedEffect(errorMessage) {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = errorMessage,
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        }
                        else -> {}
                    }
                }

                is PokemonViewState.Error -> {
                    val errorMessage = currentState.message
                    LaunchedEffect(errorMessage) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                message = errorMessage,
                                duration = SnackbarDuration.Long
                            )
                        }
                    }
                    ErrorScreen(errorMessage, modifier = modifier)
                }
            }
        }
    }
}