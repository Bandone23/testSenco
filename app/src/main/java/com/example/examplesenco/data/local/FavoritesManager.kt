package com.example.examplesenco.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "favorites_prefs")
class FavoritesManager (private val context: Context) {

    companion object {
        private fun getFavoriteKey(pokemonName: String) = booleanPreferencesKey("favorite_$pokemonName")
    }

    fun isFavorite(pokemonName: String): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[getFavoriteKey(pokemonName)] ?: false
        }
    }

    suspend fun toggleFavorite(pokemonName: String) {
        context.dataStore.edit { preferences ->
            val key = getFavoriteKey(pokemonName)
            val currentState = preferences[key] ?: false
            preferences[key] = !currentState
        }
    }

    fun getAllFavorites(): Flow<List<String>> {
        return context.dataStore.data.map { preferences ->
            preferences.asMap()
                .filterValues { it as? Boolean ?: false } // ✅ Solo valores booleanos
                .keys
                .map { it.name.removePrefix("favorite_") } // ✅ Convertimos a `String`
        }
    }
}