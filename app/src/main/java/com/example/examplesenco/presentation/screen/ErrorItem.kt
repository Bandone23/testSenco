package com.example.examplesenco.presentation.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorItem(retry: () -> Unit) {
    Text(text = "Error loading more Pokémon. Tap to retry.")
}