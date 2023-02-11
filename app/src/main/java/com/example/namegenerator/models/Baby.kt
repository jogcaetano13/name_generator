package com.example.namegenerator.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "babies")
data class Baby(
    val birth: String,
    val gender: String,
    val ethnicity: String,
    val name: String,
    val numberSameName: String,
    val rank: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)
