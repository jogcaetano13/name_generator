package com.example.namegenerator.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.namegenerator.database.daos.BabyDao
import com.example.namegenerator.models.Baby
import com.example.namegenerator.utils.Constants

@Database(
    entities = [
        Baby::class
    ],
    version = Constants.DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun babyDao(): BabyDao
}