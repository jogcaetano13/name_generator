package com.example.namegenerator.di

import com.example.namegenerator.repositories.baby.BabyRepository
import com.example.namegenerator.repositories.baby.BabyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindBabyRepository(
        impl: BabyRepositoryImpl
    ): BabyRepository
}