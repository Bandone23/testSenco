package com.example.examplesenco.di

import com.example.examplesenco.data.repository.PokemonDetailRepositoryImpl
import com.example.examplesenco.data.repository.PokemonRepositoryImpl
import com.example.examplesenco.domain.repository.PokemonDetailRepository
import com.example.examplesenco.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPokemonRepository(
        impl: PokemonRepositoryImpl
    ): PokemonRepository

    @Binds
    @Singleton
    abstract fun bindPokemonDetailRepository(
        impl: PokemonDetailRepositoryImpl
    ): PokemonDetailRepository
}