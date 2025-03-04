package com.example.examplesenco.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.examplesenco.presentation.screen.detail.PokemonDetailScreen
import com.example.examplesenco.presentation.screen.favorites.FavoritePokemonScreen
import com.example.examplesenco.presentation.screen.home.PokemonListScreen
import com.example.examplesenco.presentation.viewmodel.home.PokemonViewModel
import com.example.examplesenco.presentation.viewmodel.detail.PokemonDetailViewModel
import com.example.examplesenco.presentation.viewmodel.favorites.FavoritePokemonViewModel

sealed class Screen(val route: String) {
    object PokemonList : Screen("pokemon_list")
    object PokemonDetail : Screen("pokemon_detail/{pokemonName}") {
        fun createRoute(pokemonName: String) = "pokemon_detail/$pokemonName"
    }
    object FavoritePokemon : Screen("favorite_pokemon")
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    pokemonViewModel: PokemonViewModel,
    pokemonDetailViewModel: PokemonDetailViewModel,
    favoritePokemonViewModel: FavoritePokemonViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.PokemonList.route
    ) {
        composable(Screen.PokemonList.route) {
            PokemonListScreen(
                viewModel = pokemonViewModel,
                onPokemonClick = { pokemonName ->
                    navController.navigate(Screen.PokemonDetail.createRoute(pokemonName))
                },
                onFavoritesClick = { navController.navigate(Screen.FavoritePokemon.route) }
            )
        }

        composable(Screen.PokemonDetail.route) { backStackEntry ->
            val pokemonName = backStackEntry.arguments?.getString("pokemonName") ?: ""
            PokemonDetailScreen(
                viewModel = pokemonDetailViewModel,
                pokemonName = pokemonName,
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(Screen.FavoritePokemon.route) {
            FavoritePokemonScreen(
                viewModel = favoritePokemonViewModel,
                onPokemonClick = { pokemonName ->
                    navController.navigate(Screen.PokemonDetail.createRoute(pokemonName))
                }
            )
        }
    }
}