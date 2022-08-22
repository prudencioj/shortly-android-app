package com.jprudencio.shortly.di

import com.jprudencio.shortly.BuildConfig
import com.jprudencio.shortly.api.ShortLinkApiClient
import com.jprudencio.shortly.api.retrofit.ShrtcodeClient
import com.jprudencio.shortly.api.retrofit.adapters.ResultCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideApiClient(retrofit: Retrofit): ShortLinkApiClient {
        return retrofit.create(ShrtcodeClient::class.java)
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, @BaseUrl baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .client(okHttpClient)
            .build()

    @Provides
    fun provideOkHttpClient() =
        OkHttpClient.Builder().addInterceptor(
            HttpLoggingInterceptor().apply {
                if (BuildConfig.DEBUG) {
                    setLevel(HttpLoggingInterceptor.Level.BODY)
                } else {
                    setLevel(HttpLoggingInterceptor.Level.NONE)
                }
            }
        ).build()

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl() = BuildConfig.SHRTCO_API_URL

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class BaseUrl
}
