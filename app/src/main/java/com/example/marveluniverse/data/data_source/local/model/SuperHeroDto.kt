package com.example.marveluniverse.data.data_source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "superhero_table")
data class SuperHeroDto(
    @PrimaryKey val id: String,
    val name: String = "",
    val thumbnail: String = "",
    val description: String = ""
)
