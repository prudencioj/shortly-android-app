package com.jprudencio.shortly.di

import com.jprudencio.shortly.data.ShortLinkRepository
import com.jprudencio.shortly.data.FakeShortLinkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoriesModule::class]
)
class TestRepositoriesModule {

    @Provides
    fun provideShortLinkRepository(): ShortLinkRepository = FakeShortLinkRepository()
}
