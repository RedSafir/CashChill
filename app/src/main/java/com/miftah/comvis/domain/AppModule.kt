package com.miftah.comvis.domain

import android.content.Context
import androidx.room.Room
import com.miftah.comvis.core.local.room.AppDB
import com.miftah.comvis.core.remote.api.AppAPI
import com.miftah.comvis.utils.Constanta
import com.miftah.comvis.utils.Constanta.APP_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDB(
        @ApplicationContext context: Context
    ): AppDB =
        Room.databaseBuilder(
            context = context,
            AppDB::class.java,
            APP_DB
        ).build()


    @Provides
    @Singleton
    fun provideLogging(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    @Named("URL")
    fun provideURL(): String =
        Constanta.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(@Named("URL") url: String, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): AppAPI =
        retrofit.create(AppAPI::class.java)

    @Provides
    @Singleton
    fun provideRepository(
        api: AppAPI,
        db: AppDB
    ): Repository =
        Repository(
            api,
            db
        )
}