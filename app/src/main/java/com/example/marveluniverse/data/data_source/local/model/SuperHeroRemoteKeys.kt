package com.example.marveluniverse.data.data_source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys_table")
data class SuperHeroRemoteKeys(
    @PrimaryKey(autoGenerate = false) val id: String,
    @ColumnInfo(name = "prev_page")val prevPage: Int?,
    @ColumnInfo(name = "next_page")val nextPage: Int?
) {
    override fun toString(): String {
        return "ID $id, prevPage $prevPage, nextPage $nextPage"
    }
}
