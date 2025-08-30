package com.rezazavareh7.network.di

import com.rezazavareh.usecase.GetLanguageUseCase
import com.rezazavareh7.network.BuildConfig
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "1ee72f0e064595f1d5adcd6a78cfb5c4"
    private const val AUTHORIZATION = "Authorization"
    private const val API_ACCESS_TOKEN =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxZWU3MmYwZTA2NDU5NWYxZDVhZGNkNmE3OGNmYjVjNCIsIm5iZiI6MTU3NDg1MTA4Mi44NjQsInN1YiI6IjVkZGU1MjBhODhiYmU2MDAxODE4ZDg5YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.AuOkwjtBvIMP5A4G73ppovIMd3Aj4-AL_QS-UDtQCTg"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    fun provideMoshiConvertorFactory(moshi: Moshi): MoshiConverterFactory = MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level =
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        return logging
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(getLanguageUseCase: GetLanguageUseCase): Interceptor {
        return Interceptor { chain ->
            val language =
                runBlocking {
                    getLanguageUseCase().first()
                }
            val originalRequest = chain.request()
            val originalUrl = originalRequest.url

            val newUrl =
                originalUrl.newBuilder()
                    .setQueryParameter("language", language)
                    .build()
            val token = "Bearer $API_ACCESS_TOKEN"
            val newRequest =
                originalRequest.newBuilder()
                    .url(newUrl)
                    .addHeader(AUTHORIZATION, token)

            Timber.tag(AUTHORIZATION).i(token)

            val response = chain.proceed(newRequest.build())

            return@Interceptor response
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun providesAuthRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory,
    ): Retrofit =
        Retrofit
            .Builder()
            .addConverterFactory(moshiConverterFactory)
            .addCallAdapterFactory(ResultCallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .build()
}
