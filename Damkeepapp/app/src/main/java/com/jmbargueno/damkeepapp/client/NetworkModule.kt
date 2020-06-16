package com.jmbargueno.damkeepapp.client

import com.jmbargueno.damkeepapp.common.Constants
import com.jmbargueno.damkeepapp.service.DamKeepService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    @Named("url")
    fun provideBaseUrl(): String = Constants.BASE_URL



    @Singleton
    @Provides
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        return with(OkHttpClient.Builder()) {
            addInterceptor(tokenInterceptor)
            connectTimeout(Constants.TIMEOUT_INMILIS, TimeUnit.MILLISECONDS)
            build()
        }
    }

    @Singleton
    @Provides
    fun provideRetrofit(@Named("url") baseUrl: String, okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideTheMovieDBRetrofitService(retrofit: Retrofit): DamKeepService {
        return retrofit.create(DamKeepService::class.java)
    }
}