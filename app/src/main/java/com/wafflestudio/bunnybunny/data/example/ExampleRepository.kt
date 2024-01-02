package com.wafflestudio.bunnybunny.data.example

interface ExampleRepository {
    suspend fun exampleFunction(query: String): Boolean
}