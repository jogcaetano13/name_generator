package com.example.namegenerator.database.converters

import com.example.namegenerator.models.Baby
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class GenderTypeConvertTest {

    private lateinit var underTest: GenderTypeConverter

    @Before
    fun setup() {
        underTest = GenderTypeConverter()
    }

    @Test
    fun `given male gender, then return ordinal 0`() {
        val actual = underTest.fromType(Baby.Gender.MALE)

        assertThat(actual).isEqualTo(0)
    }

    @Test
    fun `given female gender, then return ordinal 1`() {
        val actual = underTest.fromType(Baby.Gender.FEMALE)

        assertThat(actual).isEqualTo(1)
    }

    @Test
    fun `given ordinal 0, then return male gender`() {
        val actual = underTest.fromOrdinal(0)

        assertThat(actual).isEqualTo(Baby.Gender.MALE)
    }

    @Test
    fun `given ordinal 1, then return female gender`() {
        val actual = underTest.fromOrdinal(1)

        assertThat(actual).isEqualTo(Baby.Gender.FEMALE)
    }
}