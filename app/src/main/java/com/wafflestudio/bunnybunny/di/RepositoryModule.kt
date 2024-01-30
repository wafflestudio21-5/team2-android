package com.wafflestudio.bunnybunny.di

import com.wafflestudio.bunnybunny.data.example.BindPrefRepositoryImpl
import com.wafflestudio.bunnybunny.data.example.PrefRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindPrefRepository(impl: BindPrefRepositoryImpl): PrefRepository
}