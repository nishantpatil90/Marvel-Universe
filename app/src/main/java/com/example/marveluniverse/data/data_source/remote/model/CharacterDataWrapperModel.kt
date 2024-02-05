package com.example.marveluniverse.data.data_source.remote.model

data class CharacterDataWrapperModel(
    val code: Int,
    val status: String? = null,
    val data: CharacterDataContainerModel? = null,
)
