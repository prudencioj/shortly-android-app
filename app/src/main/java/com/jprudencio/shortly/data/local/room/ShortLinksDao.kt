@file:Suppress("unused")

package com.jprudencio.shortly.data.local.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * [Room] DAO for [ShortLinkLocal] related operations.
 */
@Dao
abstract class ShortLinksDao {
    @Query("SELECT * FROM short_links")
    abstract fun getAllShortLinks(): Flow<List<ShortLinkLocal>>

    @Query("SELECT * FROM short_links WHERE id = :id")
    abstract fun shortLinkWithId(id: String): Flow<List<ShortLinkLocal>>

    @Query("SELECT COUNT(*) FROM short_links")
    abstract suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(shortLink: ShortLinkLocal): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(vararg entity: ShortLinkLocal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(entities: Collection<ShortLinkLocal>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: ShortLinkLocal)

    @Delete
    abstract suspend fun delete(entity: ShortLinkLocal): Int
}