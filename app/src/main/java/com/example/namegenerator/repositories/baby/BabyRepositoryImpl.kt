package com.example.namegenerator.repositories.baby

import com.example.namegenerator.database.daos.BabyDao
import com.example.namegenerator.models.Baby
import com.example.namegenerator.providers.files.FileProvider
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BabyRepositoryImpl @Inject constructor(
    private val dao: BabyDao,
    private val fileProvider: FileProvider
) : BabyRepository {

    override fun getRandomBaby(): Flow<Baby> = dao.getRandomBaby()

    override suspend fun replaceBabies(): List<Long> {
        if (dao.getBabyCount() > 0)
            return emptyList()

        val babiesFile = fileProvider.parseJsonString("babies.json")
        val babiesList = Gson().fromJson<List<List<String>>>(babiesFile, List::class.java)

        val babies = babiesList.map {
            Baby(it[0], it[1], it[2], it[3], it[4], it[5])
        }

        return dao.replace(babies)
    }
}