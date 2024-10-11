package com.jprudencio.shortly.di

import com.jprudencio.shortly.data.local.ShortLinkLocalDataSource
import com.jprudencio.shortly.data.remote.ShortLinkRemoteDataSourceImpl
import com.jprudencio.shortly.data.ShortLinkRepository
import com.jprudencio.shortly.data.ShortLinkRepositoryFakeImpl
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
        shortLinkRemoteDataSource: ShortLinkRemoteDataSourceImpl,
        shortLinkLocalDataSource: ShortLinkLocalDataSource,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): ShortLinkRepository =
        // FIXME API is down, using fake repository for now
        /*ShortLinkRepositoryImpl(
            shortLinkRemoteDataSource,
            shortLinkLocalDataSource,
            defaultDispatcher
        )*/
        ShortLinkRepositoryFakeImpl()
}