package com.example.namegenerator.repositories.baby

import com.example.namegenerator.api.ApiService
import com.example.namegenerator.database.daos.BabyDao
import com.example.namegenerator.models.Baby
import com.example.namegenerator.providers.files.FileProvider
import com.example.namegenerator.state.ResponseState
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BabyRepositoryImpl @Inject constructor(
    private val dao: BabyDao,
    private val fileProvider: FileProvider,
    private val apiService: ApiService
) : BabyRepository {

    override fun getRandomBaby(gender: Baby.Gender, ethnicity: String?): Flow<Baby> = dao.getRandomBaby(gender, ethnicity)

    override suspend fun replaceBabies(): List<Long> {
        if (dao.getBabyCount() > 0)
            return emptyList()

        val babiesFile = fileProvider.parseJsonString("babies.json")
        val babiesList = Gson().fromJson<List<List<String>>>(babiesFile, List::class.java)

        val babies = babiesList.map {
            Baby(it[0], Baby.Gender.fromGender(it[1]), it[2], it[3], it[4], it[5])
        }

        return dao.replace(babies)
    }

    override fun getEthnicities(): Flow<List<String>> = dao.getEthnicities()

    override fun getBabies(): Flow<ResponseState<List<Baby>>> = flow {
        emit(ResponseState.Loading)

        runCatching {
            apiService.getBabies()
        }.onSuccess { response ->
            if (response.isSuccessful && apiService.getBabies().body() != null) {
                val responseString = apiService.getBabies().body()?.data

                val babies = mutableListOf<Baby>()

                responseString?.forEach {
                    babies.add(Baby(it[8], Baby.Gender.fromGender(it[9]), it[10], it[11], it[12], it[13]))
                }

                if (babies.isNotEmpty())
                    emit(ResponseState.Success(babies))
                else
                    emit(ResponseState.Error("Babies empty"))

            } else {
                emit(ResponseState.Error("Cannot get babies"))
            }
        }.onFailure {
            emit(ResponseState.Error(it.localizedMessage ?: "Unknown"))
        }
    }
}