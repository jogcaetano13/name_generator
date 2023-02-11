package com.example.namegenerator.providers.dispatcher

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {
    override fun main(): CoroutineContext = Dispatchers.Main

    override fun background(): CoroutineContext = Dispatchers.IO
}