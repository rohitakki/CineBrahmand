package com.cine.brahmand.di

import android.content.Context
import android.content.res.Resources
import com.cine.brahmand.R
import com.cine.brahmand.api.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideBaseUrl(): String {
        return "https://api.themoviedb.org/3/"
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MovieService.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideClient(resources: Resources): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(createAuthInterceptor(resources))
            .build()
    }

    @Provides
    fun provideResources(@ApplicationContext context: Context): Resources = context.resources

    @Provides
    @Singleton
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }

    private fun createAuthInterceptor(resources: Resources): Interceptor {
        return Interceptor { chain ->
            val updatedUrl = chain.request().url.newBuilder()
                .addQueryParameter(MovieService.API_KEY_PARAM, resources.getString(R.string.api_key))
                .build()
            chain.proceed(
                chain.request().newBuilder()
                    .url(updatedUrl)
                    .build()
            )
        }
    }



}