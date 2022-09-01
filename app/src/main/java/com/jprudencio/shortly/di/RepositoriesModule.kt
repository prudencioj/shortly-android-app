package com.jprudencio.shortly.di

import com.jprudencio.shortly.data.ShortLinkLocalDataSource
import com.jprudencio.shortly.data.ShortLinkRemoteDataSource
import com.jprudencio.shortly.data.ShortLinkRepository
import com.jprudencio.shortly.data.ShortLinkRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
class RepositoriesModule {

    @Provides
    fun provideShortLinkRepository(
        shortLinkRemoteDataSource: ShortLinkRemoteDataSource,
        shortLinkLocalDataSource: ShortLinkLocalDataSource,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): ShortLinkRepository =
        ShortLinkRepositoryImpl(
            shortLinkRemoteDataSource,
            shortLinkLocalDataSource,
            defaultDispatcher
        )
}