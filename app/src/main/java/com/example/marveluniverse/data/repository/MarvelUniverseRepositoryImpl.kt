package com.example.marveluniverse.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.marveluniverse.data.data_source.MarvelSuperheroMediatorSource
import com.example.marveluniverse.data.data_source.local.MarvelDatabase
import com.example.marveluniverse.data.data_source.local.model.Favourites
import com.example.marveluniverse.data.data_source.local.model.SuperHeroDto
import com.example.marveluniverse.data.data_source.remote.MarvelApiClient
import com.example.marveluniverse.domain.repository.MarvelUniverseRepository
import com.example.marveluniverse.utils.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class MarvelUniverseRepositoryImpl @Inject constructor(
    private val marvelDb: MarvelDatabase,
    private val apiClient: MarvelApiClient,
) : MarvelUniverseRepository {

    override fun getAllSuperHeroes(): Flow<PagingData<SuperHeroDto>> {
        val pagingSourceFactory = { marvelDb.superHeroDao().getAllSuperHeroes() }
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = MarvelSuperheroMediatorSource(
                marvelDb = marvelDb,
                apiClient = apiClient
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun getAllFavourites(): Flow<List<Favourites>> {
        return marvelDb.favouritesDao().getAllFavourites()
    }

    override suspend fun hasFavouriteForId(id: String): Favourites? {
        return marvelDb.favouritesDao().getFavouriteForId(id)
    }

    override suspend fun addFavourite(favourite: Favourites) {
        marvelDb.favouritesDao().insertFavourite(favourite)
    }

    override suspend fun removeFavourite(favourite: Favourites) {
        marvelDb.favouritesDao().removeFavourite(favourite)
    }

    override fun getSuperHeroFoId(characterId: String): SuperHeroDto {
        return marvelDb.superHeroDao().getSuperHeroForId(characterId)
    }
}
