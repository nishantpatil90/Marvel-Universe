package com.example.marveluniverse.domain.use_case

import androidx.paging.PagingData
import com.example.marveluniverse.data.data_source.local.model.SuperHeroDto
import com.example.marveluniverse.domain.repository.MarvelUniverseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSuperHeroesUseCase @Inject constructor(
    private val marvelRepository: MarvelUniverseRepository,
) {

    operator fun invoke(): Flow<PagingData<SuperHeroDto>> {
        return marvelRepository.getAllSuperHeroes()
    }
}
