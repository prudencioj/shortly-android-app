package com.jprudencio.shortly.data.local.room

import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "short_links",
    indices = [
        Index("id", unique = true)
    ]
)
@Immutable
data class ShortLinkLocal(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "short_link") val shortLink: String,
    @ColumnInfo(name = "original_link") val originalLink: String
)