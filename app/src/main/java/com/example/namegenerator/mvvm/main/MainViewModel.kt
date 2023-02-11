package com.example.namegenerator.mvvm.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.namegenerator.providers.dispatcher.DispatcherProvider
import com.example.namegenerator.repositories.baby.BabyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val babyRepository: BabyRepository
) : ViewModel() {

    private val _isLoadingState = MutableStateFlow(true)
    val isLoadingState: StateFlow<Boolean> = _isLoadingState

    init {
        replaceBabies()
    }

    private fun replaceBabies() = viewModelScope.launch(dispatcherProvider.background()) {
        babyRepository.replaceBabies()
        _isLoadingState.value = false
    }
}