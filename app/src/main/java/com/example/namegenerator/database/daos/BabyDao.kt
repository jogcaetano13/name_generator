package com.example.namegenerator.database.daos

import androidx.room.*
import com.example.namegenerator.models.Baby
import kotlinx.coroutines.flow.Flow

@Dao
interface BabyDao {

    @Query("SELECT * FROM babies WHERE (:ethnicity IS NULL OR ethnicity = :ethnicity) AND gender = :gender ORDER BY RANDOM() LIMIT 1")
    fun getRandomBaby(gender: Baby.Gender, ethnicity: String?): Flow<Baby>

    @Query("SELECT DISTINCT ethnicity FROM babies")
    fun getEthnicities(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(babies: List<Baby>): List<Long>

    @Query("SELECT COUNT(*) FROM babies")
    suspend fun getBabyCount(): Int

    @Transaction
    suspend fun replace(babies: List<Baby>): List<Long> {
        deleteAll()
        return insert(babies)
    }

    @Query("DELETE FROM babies")
    suspend fun deleteAll(): Int
}