package com.example.marveluniverse.data.data_source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.marveluniverse.domain.model.SuperHero

@Entity(tableName = "superhero_table")
data class SuperHeroDto(
    @PrimaryKey val id: String,
    val name: String = "",
    val thumbnail: String = "",
    val description: String = ""
)

fun SuperHeroDto.toSuperHero(isFavourite: Boolean): SuperHero {
    return SuperHero(
        id = id,
        name = name,
        thumbnail = thumbnail,
        description = description,
        isFavourite = isFavourite
    )
}
