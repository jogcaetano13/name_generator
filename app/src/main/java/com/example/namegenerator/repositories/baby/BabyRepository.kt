package com.example.namegenerator.repositories.baby

import com.example.namegenerator.models.Baby
import com.example.namegenerator.state.ResponseState
import kotlinx.coroutines.flow.Flow

interface BabyRepository {
    fun getRandomBaby(gender: Baby.Gender, ethnicity: String?): Flow<Baby>

    suspend fun replaceBabies(): List<Long>

    fun getEthnicities(): Flow<List<String>>

    fun getBabies(): Flow<ResponseState<List<Baby>>>
}