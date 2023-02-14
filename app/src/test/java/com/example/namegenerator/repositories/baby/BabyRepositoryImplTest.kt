package com.example.namegenerator.repositories.baby

import app.cash.turbine.test
import com.example.namegenerator.database.daos.BabyDao
import com.example.namegenerator.models.Baby
import com.example.namegenerator.providers.files.FileProvider
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.given
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class BabyRepositoryImplTest {
    private val dao = mock<BabyDao>()
    private val fileProvider = mock<FileProvider>()

    private val babiesJson = "[[\"2016\", \"MALE\", \"WHITE NON HISPANIC\", \"Michael\", \"260\", \"2\"]]"

    private lateinit var underTest: BabyRepositoryImpl

    @Before
    fun setup() {
        underTest = BabyRepositoryImpl(dao, fileProvider)
    }

    @Test
    fun `given random baby with male gender, then return baby with male gender`() = runTest {
        given(dao.getRandomBaby(Baby.Gender.MALE)).willReturn(flowOf(
            Baby(
                "2016",
                Baby.Gender.MALE,
                "ASIAN AND PACIFIC ISLANDER",
                "Michael",
                "172",
                "1"
            )
        ))

        underTest.getRandomBaby(Baby.Gender.MALE).test {
            val actual = awaitItem()
            awaitComplete()

            assertThat(actual.name).isEqualTo("Michael")
        }
    }

    @Test
    fun `given random baby with female gender, then return baby with female gender`() = runTest {
        given(dao.getRandomBaby(Baby.Gender.FEMALE)).willReturn(flowOf(
            Baby(
                "2016",
                Baby.Gender.FEMALE,
                "ASIAN AND PACIFIC ISLANDER",
                "Olivia",
                "172",
                "1"
            )
        ))

        underTest.getRandomBaby(Baby.Gender.FEMALE).test {
            val actual = awaitItem()
            awaitComplete()

            assertThat(actual.name).isEqualTo("Olivia")
        }
    }

    @Test
    fun `given baby count more than 0, then return replace with empty list`() = runTest {
        given(dao.getBabyCount()).willReturn(1)
        val actual = underTest.replaceBabies()

        assertThat(actual).isEmpty()
    }

    @Test
    fun `given baby count 0, then return replace with not empty list`() = runTest {
        val babyList = Gson().fromJson<List<List<String>>>(babiesJson, List::class.java)
        val babiesToInsert = babyList.map {
            Baby(it[0], Baby.Gender.fromGender(it[1]), it[2], it[3], it[4], it[5])
        }

        given(dao.getBabyCount()).willReturn(0)
        given(fileProvider.parseJsonString(anyString())).willReturn(babiesJson)
        given(dao.replace(babiesToInsert)).willReturn(listOf(1))
        val actual = underTest.replaceBabies()

        assertThat(actual).isNotEmpty()
    }
}