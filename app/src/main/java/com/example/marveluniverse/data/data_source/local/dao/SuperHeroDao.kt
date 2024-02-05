package com.example.marveluniverse.data.data_source.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.marveluniverse.data.data_source.local.model.SuperHeroDto
import kotlinx.coroutines.flow.Flow

@Dao
interface SuperHeroDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(superheroes: List<SuperHeroDto>)

    @Query("SELECT * FROM superhero_table")
    fun getAllSuperHeroes(): PagingSource<Int, SuperHeroDto>

    @Query("SELECT * FROM superhero_table where id = :id LIMIT 1")
    fun getSuperHeroForId(id: String): SuperHeroDto

    @Query("DELETE FROM superhero_table")
    suspend fun clearAllSuperHeroes()
}
