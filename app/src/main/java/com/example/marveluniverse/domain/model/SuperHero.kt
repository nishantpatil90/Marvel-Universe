package com.example.marveluniverse.domain.model

data class SuperHero(
    val id: String,
    val name: String = "",
    val thumbnail: String = "",
    val description: String = "",
    val isFavourite: Boolean = false
)
