package ru.vsls.surfquiz.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.components.SingletonComponent
import dagger.hilt.InstallIn
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.vsls.surfquiz.data.remote.QuizApiService
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private  const val BASE_URL = "https://opentdb.com/"

    @Provides
    @Reusable
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val contentType = "application/json".toMediaType()
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(json.asConverterFactory(contentType).apply {  })
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    fun provideTracksService(retrofit: Retrofit): QuizApiService {
        return retrofit.create(QuizApiService::class.java)
    }
}
