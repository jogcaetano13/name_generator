package com.example.namegenerator.api

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("api/views/25th-nujf/rows.json")
    suspend fun getBabies(): Response<BabyResponse>
}