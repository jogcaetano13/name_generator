package com.example.namegenerator.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "babies")
data class Baby(
    val birth: String,
    val gender: Gender,
    val ethnicity: String,
    val name: String,
    val numberSameName: String,
    val rank: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) {
    enum class Gender(val gender: String) {
        MALE("MALE"),
        FEMALE("FEMALE");

        companion object {
            fun fromGender(gender: String) = values().find { it.gender == gender } ?: error("Gender $gender not found")
        }
    }
}
