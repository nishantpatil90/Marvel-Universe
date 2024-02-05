package com.example.marveluniverse.data.data_source

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.marveluniverse.data.data_source.local.MarvelDatabase
import com.example.marveluniverse.data.data_source.local.model.SuperHeroDto
import com.example.marveluniverse.data.data_source.local.model.SuperHeroRemoteKeys
import com.example.marveluniverse.data.data_source.remote.MarvelApiClient
import com.example.marveluniverse.utils.PAGE_SIZE

@OptIn(ExperimentalPagingApi::class)
class MarvelSuperheroMediatorSource (
    private val marvelDb: MarvelDatabase,
    private val apiClient: MarvelApiClient
) : RemoteMediator<Int, SuperHeroDto>() {

    private val superHeroDao = marvelDb.superHeroDao()
    private val remoteKeyDao = marvelDb.remoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SuperHeroDto>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {

                /*Generally, this means that a request to load remote data and replace all local data was made.*/
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }

                LoadType.PREPEND -> {
                    //Here there will always be an item, because it was already saved when in REFRESH.
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            /*if false no data is saved in the local database, requests data from the network.
                            * if true has reached the end of the page, stops placing items at the beginning and
                            * goes to the next loadType.
                            * */
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    //Here there will always be an item, because it was already saved when in REFRESH.
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = apiClient.getCharacterWrapper(
                offset = getOffsetByPage(currentPage),
                limit = PAGE_SIZE
            )

            val characters = response.data?.results?.filter { !it.id.isNullOrBlank() }
                ?: return MediatorResult.Error(java.lang.Exception())
            val endOfPaginationReached = characters.isEmpty()

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            marvelDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    //initial load, clear all data from db
                    superHeroDao.clearAllSuperHeroes()
                    remoteKeyDao.deleteAllRemoteKeys()
                }
                val keys = characters.map { character ->
                    SuperHeroRemoteKeys(
                        id = character.id ?: "",
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }

                val superHeroes = characters.map {
                    val thumbnailUrl = "${it.thumbnail?.path}.${it.thumbnail?.extension}"
                    SuperHeroDto(
                        id = it.id ?: "",
                        name = it.name ?: "",
                        description = it.description ?: "",
                        thumbnail = thumbnailUrl.replace("http", "https")
                    )
                }
                remoteKeyDao.addAllRemoteKeys(remoteKeys = keys)
                superHeroDao.upsertAll(superHeroes)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            Log.d("####### ", "MarvelSuperheroMediatorSource#load: $e")
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, SuperHeroDto>
    ): SuperHeroRemoteKeys? {
        Log.d("MEDIATOR", "state.anchorPosition ${state.anchorPosition}")

        return state.anchorPosition?.let { position ->
            Log.d("MEDIATOR", "position $position")
            state.closestItemToPosition(position)?.id?.let { id ->

                remoteKeyDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, SuperHeroDto>
    ): SuperHeroRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { comic ->
                remoteKeyDao.getRemoteKeys(id = comic.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, SuperHeroDto>
    ): SuperHeroRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { comic ->
                remoteKeyDao.getRemoteKeys(id = comic.id)
            }
    }

    private fun getOffsetByPage(page: Int): Int {
        return if (page == 1) 0 else (page - 1) * (PAGE_SIZE)
    }
}
