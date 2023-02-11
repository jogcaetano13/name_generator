package com.example.namegenerator.utils

import com.example.namegenerator.providers.dispatcher.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class TestDispatcherProvider : DispatcherProvider {
    override fun main(): CoroutineContext = UnconfinedTestDispatcher()

    override fun background(): CoroutineContext = UnconfinedTestDispatcher()
}