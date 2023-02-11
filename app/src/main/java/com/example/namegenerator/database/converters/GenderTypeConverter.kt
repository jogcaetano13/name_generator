package com.example.namegenerator.database.converters

import androidx.room.TypeConverter
import com.example.namegenerator.models.Baby

class GenderTypeConverter {

    @TypeConverter
    fun fromType(type: Baby.Gender?) = type?.ordinal

    @TypeConverter
    fun fromOrdinal(ordinal: Int?) = ordinal?.let { Baby.Gender.values()[it] }
}