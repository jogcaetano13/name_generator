package com.example.namegenerator.providers.files

interface FileProvider {
    fun parseJsonString(filename: String): String
}