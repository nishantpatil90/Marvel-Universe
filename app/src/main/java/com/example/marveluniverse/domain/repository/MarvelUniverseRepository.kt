package com.example.marveluniverse.domain.repository

import androidx.paging.PagingData
import com.example.marveluniverse.data.data_source.local.model.Favourites
import com.example.marveluniverse.data.data_source.local.model.SuperHeroDto
import kotlinx.coroutines.flow.Flow

interface MarvelUniverseRepository {

    fun getAllSuperHeroes(): Flow<PagingData<SuperHeroDto>>
    fun getAllFavourites(): Flow<List<Favourites>>
    suspend fun hasFavouriteForId(id: String): Favourites?
    suspend fun addFavourite(favourite: Favourites)
    suspend fun removeFavourite(favourite: Favourites)
    fun getSuperHeroFoId(characterId: String): SuperHeroDto
}
