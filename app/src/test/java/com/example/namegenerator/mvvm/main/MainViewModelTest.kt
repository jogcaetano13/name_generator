package com.example.namegenerator.mvvm.main

import app.cash.turbine.test
import com.example.namegenerator.repositories.baby.BabyRepository
import com.example.namegenerator.utils.TestDispatcherProvider
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class MainViewModelTest {
    private val repository = mock<BabyRepository>()

    private lateinit var underTest: MainViewModel

    @Before
    fun setup() {
        underTest = MainViewModel(
            TestDispatcherProvider(),
            repository
        )
    }

    @Test
    fun `replace babies, then return isLoading state false`() = runTest {
        given(repository.replaceBabies()).willReturn(emptyList())

        underTest.isLoadingState.test {
            val actual = awaitItem()

            assertThat(actual).isFalse()
        }
    }
}