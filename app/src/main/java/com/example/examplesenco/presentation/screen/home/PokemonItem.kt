package com.example.examplesenco.presentation.screen.home

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.examplesenco.R
import com.example.examplesenco.data.model.PokemonItem
import com.example.examplesenco.utils.getColorForPokemonType
import com.example.examplesenco.utils.getPokemonIdFromUrl
import com.example.examplesenco.utils.getPokemonImageUrl

@Composable
fun PokemonItemS(pokemon: PokemonItem,pokemonType: String ,onClick: () -> Unit) {
    val pokemonId = getPokemonIdFromUrl(pokemon.url)
    val imageUrl = getPokemonImageUrl(pokemonId)
    val backgroundColor = getColorForPokemonType(pokemonType)


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {

            Crossfade(targetState = imageUrl, label = "ImageCrossfade") { image ->
                AsyncImage(
                    model = image,
                    contentDescription = "${pokemon.name} image",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape), // 🔥 Evita bordes duros
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.placeholder_image), // 🔥 Imagen temporal
                    error = painterResource(R.drawable.placeholder_image) // 🔥 Imagen de error si falla la carga
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = pokemon.name.capitalize(),
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}
