package com.rishav.tmdbapp.network

import com.rishav.tmdbapp.util.ApplicationConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object NetworkModule {
    private val logging = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(Interceptor { chain ->
            val request = chain.request()
            val originalUrl = request.url
            val url = originalUrl.newBuilder()
                .addQueryParameter("api_key", "api_key_here")
                .build()
            val requestBuilder = request.newBuilder()
                .url(url)
            val newRequest = requestBuilder.build()
            chain.proceed(newRequest)
        })
        .build()
    @Singleton
    @Provides
    fun provideRetrofit() : NetworkApi{
        return Retrofit.Builder()
            .baseUrl(ApplicationConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(NetworkApi::class.java)
    }
}