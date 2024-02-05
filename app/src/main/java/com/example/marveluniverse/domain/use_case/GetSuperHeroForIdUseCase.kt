package com.example.marveluniverse.domain.use_case

import com.example.marveluniverse.data.repository.MarvelUniverseRepository
import com.example.marveluniverse.domain.model.SuperHero
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetSuperHeroForIdUseCase @Inject constructor(
    private val marvelRepository: MarvelUniverseRepository,
) {

    operator fun invoke(characterId: String): Flow<SuperHero> {
        val superHeroDto = marvelRepository.getSuperHeroFoId(characterId)
        return marvelRepository.getAllFavourites().map { favourites ->
            favourites.asSequence()
                .filter { it.id == characterId }
                .map {
                    SuperHero(
                        id = superHeroDto.id,
                        thumbnail = superHeroDto.thumbnail,
                        name = superHeroDto.name,
                        description = superHeroDto.description,
                        isFavourite = true
                    )
                }.take(1)
                .firstOrNull()
                ?: SuperHero(
                    id = superHeroDto.id,
                    thumbnail = superHeroDto.thumbnail,
                    name = superHeroDto.name,
                    description = superHeroDto.description,
                    isFavourite = false
                )
        }.distinctUntilChanged()
    }
}
