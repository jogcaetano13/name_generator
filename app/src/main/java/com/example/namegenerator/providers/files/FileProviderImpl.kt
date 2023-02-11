package com.example.namegenerator.providers.files

import android.content.Context
import javax.inject.Inject

class FileProviderImpl @Inject constructor(
    private val context: Context
) : FileProvider {

    override fun parseJsonString(filename: String): String = context.assets.open(filename).bufferedReader().use {
        it.readText()
    }
}