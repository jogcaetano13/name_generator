package com.example.namegenerator.providers.dispatcher

import kotlin.coroutines.CoroutineContext

interface DispatcherProvider {
    fun main(): CoroutineContext
    fun background(): CoroutineContext
}