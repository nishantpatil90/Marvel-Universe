package com.example.marveluniverse.domain.use_case

import com.example.marveluniverse.data.data_source.local.model.Favourites
import com.example.marveluniverse.domain.repository.MarvelUniverseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(
    private val marvelRepository: MarvelUniverseRepository,
) {

    operator fun invoke(): Flow<List<Favourites>> {
        return marvelRepository.getAllFavourites()
    }
}
