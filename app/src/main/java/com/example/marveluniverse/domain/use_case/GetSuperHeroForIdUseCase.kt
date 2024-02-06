package com.example.marveluniverse.domain.use_case

import com.example.marveluniverse.data.data_source.local.model.toSuperHero
import com.example.marveluniverse.domain.model.SuperHero
import com.example.marveluniverse.domain.repository.MarvelUniverseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSuperHeroForIdUseCase @Inject constructor(
    private val marvelRepository: MarvelUniverseRepository,
) {

    operator fun invoke(characterId: String): Flow<SuperHero> {
        val superHeroDto = marvelRepository.getSuperHeroFoId(characterId)
        return marvelRepository.getAllFavourites()
            .map { favourites ->
                favourites.asSequence()
                    .filter { it.id == characterId }
                    .map { superHeroDto.toSuperHero(true) }
                    .take(1)
                    .firstOrNull()
                    ?: superHeroDto.toSuperHero(false)
            }
            .distinctUntilChanged()
    }
}
