package com.example.examplesenco.presentation.screen.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ErrorScreen(message: String, modifier: Modifier = Modifier) {
    Text(text = "Error: $message")
}