package com.example.marveluniverse.data.data_source.remote.model

import com.example.marveluniverse.data.data_source.local.model.SuperHeroDto

data class CharacterModel(
    var id: String? = null,
    var name: String? = null,
    var description: String? = null,
    var thumbnail: ImageModel?,
)

fun CharacterModel.toSuperHerDto(): SuperHeroDto? {
    if (id.isNullOrBlank() || name.isNullOrBlank()) {
        return null
    }
    return SuperHeroDto(
        id = id ?: "",
        name = name ?: "",
        thumbnail = "${thumbnail?.path}.${thumbnail?.extension}".replace("http", "https"),
        description = description ?: "",
    )
}
