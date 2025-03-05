package com.example.examplesenco.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.examplesenco.data.local.dao.PokemonDao
import com.example.examplesenco.data.remote.ApiService
import com.example.examplesenco.data.repository.datasource.PokemonSourceFactory
import com.example.examplesenco.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePokemonRepository(
        apiService: ApiService,
        pokemonDao: PokemonDao,
        @ApplicationContext context: Context
    ): PokemonRepository {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return PokemonSourceFactory(apiService, pokemonDao, connectivityManager)
    }
}