package com.example.marveluniverse.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.marveluniverse.data.data_source.local.dao.FavouritesDao
import com.example.marveluniverse.data.data_source.local.dao.RemoteKeyDao
import com.example.marveluniverse.data.data_source.local.dao.SuperHeroDao
import com.example.marveluniverse.data.data_source.local.model.Favourites
import com.example.marveluniverse.data.data_source.local.model.SuperHeroDto
import com.example.marveluniverse.data.data_source.local.model.SuperHeroRemoteKeys

@Database(
    entities = [SuperHeroDto::class, SuperHeroRemoteKeys::class, Favourites::class],
    version = 1
)
abstract class MarvelDatabase : RoomDatabase() {

    abstract fun superHeroDao(): SuperHeroDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun favouritesDao(): FavouritesDao
}
