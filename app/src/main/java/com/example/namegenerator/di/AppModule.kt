package com.example.namegenerator.di

import android.content.Context
import androidx.room.Room
import com.example.namegenerator.api.ApiService
import com.example.namegenerator.database.AppDatabase
import com.example.namegenerator.database.daos.BabyDao
import com.example.namegenerator.providers.dispatcher.DispatcherProvider
import com.example.namegenerator.providers.dispatcher.DispatcherProviderImpl
import com.example.namegenerator.providers.files.FileProvider
import com.example.namegenerator.providers.files.FileProviderImpl
import com.example.namegenerator.utils.Constants
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideFileProvider(
        @ApplicationContext context: Context
    ): FileProvider = FileProviderImpl(context)

    @Singleton
    @Provides
    fun provideRetrofit(): ApiService = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create()

    @Singleton
    @Provides
    fun provideBabyDao(
        database: AppDatabase
    ): BabyDao = database.babyDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModuleAbstract {

    @Binds
    abstract fun bindDispatcherProvider(
        impl: DispatcherProviderImpl
    ): DispatcherProvider
}