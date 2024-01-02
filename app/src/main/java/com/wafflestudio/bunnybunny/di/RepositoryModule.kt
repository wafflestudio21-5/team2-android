package com.wafflestudio.bunnybunny.di

import com.wafflestudio.bunnybunny.data.example.ExampleRepository
import com.wafflestudio.bunnybunny.data.example.ExampleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindsExampleRepository(impl: ExampleRepositoryImpl): ExampleRepository

}