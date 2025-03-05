package com.example.examplesenco.presentation.screen.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.examplesenco.presentation.viewmodel.favorites.FavoritePokemonViewModel
import com.example.examplesenco.utils.getPokemonIdFromUrl
import com.example.examplesenco.utils.getPokemonImageUrl


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritePokemonScreen(
    viewModel: FavoritePokemonViewModel = hiltViewModel(),
    onPokemonClick: (String) -> Unit
) {
    val favoritePokemonList by viewModel.favoritePokemonList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Pokémones Favoritos") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (favoritePokemonList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No tienes Pokémones favoritos aún.", fontWeight = FontWeight.Bold)
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(favoritePokemonList) { pokemonUrl ->
                        FavoritePokemonItem(
                            pokemonName = pokemonUrl,
                            pokemonUrl = getPokemonImageUrl(getPokemonIdFromUrl(pokemonUrl)),
                            onClick = { onPokemonClick(pokemonUrl) }
                        )
                    }
                }
            }
        }
    }
}

