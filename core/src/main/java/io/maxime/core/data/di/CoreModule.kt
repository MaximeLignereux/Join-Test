package io.maxime.core.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.maxime.core.BuildConfig
import io.maxime.core.data.local.AppDatabase
import io.maxime.core.data.local.dao.CoreDao
import io.maxime.core.data.remote.CoreService
import io.maxime.core.util.FlowCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.MINUTES)
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun providesCoreService(okHttpClient: OkHttpClient): CoreService =
        Retrofit.Builder()
            .baseUrl("https://api.stories.studio/v2/teams/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory())
            .build()
            .create(CoreService::class.java)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "JoinStories"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideCoreDao(appDatabase: AppDatabase): CoreDao = appDatabase.coreDao()

}
