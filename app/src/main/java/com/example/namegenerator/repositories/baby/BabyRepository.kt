package com.example.namegenerator.repositories.baby

import com.example.namegenerator.models.Baby
import kotlinx.coroutines.flow.Flow

interface BabyRepository {
    fun getRandomBaby(gender: Baby.Gender): Flow<Baby>

    suspend fun replaceBabies(): List<Long>
}