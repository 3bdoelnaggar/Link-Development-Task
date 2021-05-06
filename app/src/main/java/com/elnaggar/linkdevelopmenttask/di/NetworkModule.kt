package com.elnaggar.linkdevelopmenttask.di

import androidx.viewbinding.BuildConfig
import com.elnaggar.linkdevelopmenttask.HiltApplication
import com.elnaggar.linkdevelopmenttask.data.remote.service.NewsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor?): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (interceptor != null) {
                addInterceptor(interceptor)
            }
            connectTimeout(3, TimeUnit.SECONDS)
            readTimeout(3, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    fun provideLoggerInterceptor(): HttpLoggingInterceptor? {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            null
        }
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {
        val baseUrl = "https://newsapi.org/v1/"
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(moshiConverterFactory).build()
    }

    @Provides
    fun provideNewsService(retrofit: Retrofit): NewsService {
        return retrofit.create(NewsService::class.java)
    }

    @Provides
    fun provideMoshiConverterFactory(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }
}