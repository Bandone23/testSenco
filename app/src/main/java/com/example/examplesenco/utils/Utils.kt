package com.example.examplesenco.utils


import android.content.Context
import android.content.Intent
import androidx.compose.ui.graphics.Color
import java.util.Locale

fun getPokemonIdFromUrl(url: String): String {
    return url.trimEnd('/').split("/").last()
}

fun getPokemonImageUrl(pokemonId: String): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$pokemonId.png"
}


fun getColorForPokemonType(type: String): Color {
    return when (type.lowercase()) {
        "fire" -> Color(0xFFFFA07A)
        "water" -> Color(0xFF87CEFA)
        "grass" -> Color(0xFF98FB98)
        "electric" -> Color(0xFFFFD700)
        "ice" -> Color(0xFFB0E0E6)
        "fighting" -> Color(0xFFC04040)
        "poison" -> Color(0xFF9B30FF)
        "ground" -> Color(0xFFD2B48C)
        "flying" -> Color(0xFFAEC6CF)
        "psychic" -> Color(0xFFEE82EE)
        "bug" -> Color(0xFFADFF2F)
        "rock" -> Color(0xFF8B4513)
        "ghost" -> Color(0xFF705898)
        "dragon" -> Color(0xFF6A5ACD)
        "dark" -> Color(0xFF4F4F4F)
        "steel" -> Color(0xFFB0C4DE)
        "fairy" -> Color(0xFFFFB6C1)
        else -> Color.LightGray
    }
}

fun sharePokemon(context: Context, name: String, imageUrl: String?) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, "Check out this Pokémon!")
        putExtra(Intent.EXTRA_TEXT, "Pokemon: $name\nImage: ${imageUrl ?: "No image available"}")
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share via"))
}