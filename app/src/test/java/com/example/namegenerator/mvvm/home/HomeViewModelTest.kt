package com.example.namegenerator.mvvm.home

import app.cash.turbine.test
import com.example.namegenerator.models.Baby
import com.example.namegenerator.repositories.baby.BabyRepository
import com.example.namegenerator.utils.TestDispatcherProvider
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    private val repository = mock<BabyRepository>()

    private lateinit var underTest: HomeViewModel

    @Before
    fun setup() {
        underTest = HomeViewModel(
            TestDispatcherProvider(),
            repository
        )
    }

    @Test
    fun `given baby with female gender, then return baby state with female baby`() = runTest {
        given(repository.getRandomBaby(Baby.Gender.FEMALE)).willReturn(flowOf(
            Baby(
                "2016",
                Baby.Gender.FEMALE,
                "ASIAN AND PACIFIC ISLANDER",
                "Olivia",
                "172",
                "1"
            )
        ))

        underTest.getRandomBaby(Baby.Gender.FEMALE)

        underTest.babyState.test {
            val actual = awaitItem()

            assertThat(actual?.name).isEqualTo("Olivia")
        }
    }

    @Test
    fun `given baby with male gender, then return baby state with male baby`() = runTest {
        given(repository.getRandomBaby(Baby.Gender.MALE)).willReturn(flowOf(
            Baby(
                "2016",
                Baby.Gender.MALE,
                "ASIAN AND PACIFIC ISLANDER",
                "Michael",
                "172",
                "1"
            )
        ))

        underTest.getRandomBaby(Baby.Gender.MALE)

        underTest.babyState.test {
            val actual = awaitItem()

            assertThat(actual?.name).isEqualTo("Michael")
        }
    }
}