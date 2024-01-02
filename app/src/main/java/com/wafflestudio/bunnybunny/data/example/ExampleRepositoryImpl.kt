package com.wafflestudio.bunnybunny.data.example

import com.wafflestudio.bunnybunny.lib.network.api.ExampleApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExampleRepositoryImpl @Inject constructor(
    private val api: ExampleApi,
): ExampleRepository {
    override suspend fun exampleFunction(query: String): Boolean {
        TODO("Not yet implemented")
    }
}