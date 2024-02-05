package com.example.marveluniverse.data.data_source.remote

import com.example.marveluniverse.data.data_source.remote.model.CharacterDataWrapperModel
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApiClient {

    @GET("v1/public/characters")
    suspend fun getCharacterWrapper(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): CharacterDataWrapperModel
}
