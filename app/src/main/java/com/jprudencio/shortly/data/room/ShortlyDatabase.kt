package com.jprudencio.shortly.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * The [RoomDatabase] we use in this app.
 */
@Database(
    entities = [
        ShortLinkLocal::class
    ],
    version = 1,
    exportSchema = false
)

abstract class ShortlyDatabase : RoomDatabase() {
    abstract fun shortLinksDao(): ShortLinksDao
}