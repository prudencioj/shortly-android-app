package com.jprudencio.shortly.di

import android.content.Context
import androidx.room.Room
import com.jprudencio.shortly.data.local.room.ShortLinksDao
import com.jprudencio.shortly.data.local.room.ShortlyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): ShortlyDatabase {
        return Room.databaseBuilder(
            context,
            ShortlyDatabase::class.java,
            "shortly-db"
        ).build()
    }

    @Provides
    fun provideShortLinksDao(database: ShortlyDatabase): ShortLinksDao {
        return database.shortLinksDao()
    }
}