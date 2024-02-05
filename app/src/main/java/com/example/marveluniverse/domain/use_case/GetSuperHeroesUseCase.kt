package com.example.marveluniverse.domain.use_case

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.marveluniverse.data.data_source.local.model.Favourites
import com.example.marveluniverse.data.data_source.local.model.SuperHeroDto
import com.example.marveluniverse.data.repository.MarvelUniverseRepository
import com.example.marveluniverse.domain.model.SuperHero
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetSuperHeroesUseCase @Inject constructor(
    private val marvelRepository: MarvelUniverseRepository,
) {

    operator fun invoke(): Flow<PagingData<SuperHeroDto>> {
        return marvelRepository.getAllSuperHeroes()
    }
}
