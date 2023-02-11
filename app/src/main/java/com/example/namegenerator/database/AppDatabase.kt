package com.example.namegenerator.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.namegenerator.database.converters.GenderTypeConverter
import com.example.namegenerator.database.daos.BabyDao
import com.example.namegenerator.models.Baby
import com.example.namegenerator.utils.Constants

@TypeConverters(
    GenderTypeConverter::class
)
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