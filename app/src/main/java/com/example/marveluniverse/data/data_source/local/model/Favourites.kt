package com.example.marveluniverse.data.data_source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites_table")
data class Favourites(@PrimaryKey val id: String)
