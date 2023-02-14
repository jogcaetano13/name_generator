package com.example.namegenerator.providers.files

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class FileProviderImplTest {
    private lateinit var context: Context

    private lateinit var underTest: FileProviderImpl

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        underTest = FileProviderImpl(context)
    }

    @Test
    fun givenJsonFile_ReturnContentString() {
        val actual = underTest.parseJsonString("babies.json")

        assertThat(actual).contains("MALE")
    }
}