package com.example.marveluniverse.data.data_source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marveluniverse.data.data_source.local.model.Favourites
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavourite(favourites: Favourites)

    @Delete
    suspend fun removeFavourite(favourites: Favourites)

    @Query("SELECT * FROM favourites_table where id = :id")
    suspend fun getFavouriteForId(id: String): Favourites?

    @Query("SELECT * FROM favourites_table")
    fun getAllFavourites(): Flow<List<Favourites>>
}
