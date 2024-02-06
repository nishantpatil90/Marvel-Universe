package com.example.marveluniverse.di

import com.example.marveluniverse.data.repository.MarvelUniverseRepositoryImpl
import com.example.marveluniverse.domain.repository.MarvelUniverseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideMediaRepository(marvelUniverseRepositoryImpl: MarvelUniverseRepositoryImpl): MarvelUniverseRepository
}
