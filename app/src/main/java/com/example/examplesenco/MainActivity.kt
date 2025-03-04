package com.example.examplesenco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.example.examplesenco.presentation.viewmodel.home.PokemonViewModel
import com.example.examplesenco.presentation.viewmodel.detail.PokemonDetailViewModel
import com.example.examplesenco.presentation.viewmodel.favorites.FavoritePokemonViewModel
import com.example.examplesenco.ui.navigation.AppNavGraph
import com.example.examplesenco.ui.theme.ExampleSencoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val pokemonViewModel: PokemonViewModel = hiltViewModel()
            val pokemonDetailViewModel: PokemonDetailViewModel = hiltViewModel()
            val favoritePokemonViewModel: FavoritePokemonViewModel = hiltViewModel()

            ExampleSencoTheme {
                AppNavGraph(
                    navController = navController,
                    pokemonViewModel = pokemonViewModel,
                    pokemonDetailViewModel = pokemonDetailViewModel,
                    favoritePokemonViewModel = favoritePokemonViewModel
                )
            }
        }
    }
}
