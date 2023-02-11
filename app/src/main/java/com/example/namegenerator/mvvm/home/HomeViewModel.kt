package com.example.namegenerator.mvvm.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.namegenerator.models.Baby
import com.example.namegenerator.providers.dispatcher.DispatcherProvider
import com.example.namegenerator.repositories.baby.BabyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val repository: BabyRepository
) : ViewModel() {

    private val _babyState = MutableStateFlow<Baby?>(null)
    val babyState: StateFlow<Baby?> = _babyState

    fun getRandomBaby(gender: Baby.Gender) = viewModelScope.launch(dispatcherProvider.background()) {
        repository.getRandomBaby(gender).collectLatest {
            _babyState.value = it
        }
    }
}