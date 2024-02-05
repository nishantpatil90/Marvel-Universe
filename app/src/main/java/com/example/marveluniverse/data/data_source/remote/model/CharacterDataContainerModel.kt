package com.example.marveluniverse.data.data_source.remote.model

data class CharacterDataContainerModel(
    val limit: String? = null,
    val total: String? = null,
    val count: String? = null,
    val results: ArrayList<CharacterModel>? = null,
)
