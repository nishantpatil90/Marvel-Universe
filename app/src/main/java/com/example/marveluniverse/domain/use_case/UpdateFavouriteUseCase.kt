package com.example.marveluniverse.domain.use_case

import com.example.marveluniverse.data.data_source.local.model.Favourites
import com.example.marveluniverse.data.repository.MarvelUniverseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateFavouriteUseCase @Inject constructor(
    private val marvelRepository: MarvelUniverseRepository,
) {

    suspend operator fun invoke(id: String) {
        withContext(Dispatchers.IO) {
            val favourite = marvelRepository.hasFavouriteForId(id)
            if (favourite != null) {
                marvelRepository.removeFavourite(favourite)
            } else {
                marvelRepository.addFavourite(Favourites(id))
            }

        }
    }
}
