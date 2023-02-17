package com.example.namegenerator.mvvm.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.namegenerator.models.Baby
import com.example.namegenerator.providers.dispatcher.DispatcherProvider
import com.example.namegenerator.repositories.baby.BabyRepository
import com.example.namegenerator.state.ResponseState
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

    private val _babyGender = MutableStateFlow<Baby.Gender?>(null)
    val babyGender: StateFlow<Baby.Gender?> = _babyGender

    private val _ethnicitySelected = MutableStateFlow<String?>(null)
    val ethnicitySelected: StateFlow<String?> = _ethnicitySelected

    private val _ethnicities = MutableStateFlow<List<String>>(emptyList())
    val ethnicities: StateFlow<List<String>> = _ethnicities

    private val _babiesState = MutableStateFlow<ResponseState<List<Baby>>>(ResponseState.Empty)
    val babiesState: StateFlow<ResponseState<List<Baby>>> = _babiesState

    private val _toastError = MutableStateFlow<String?>(null)
    val toastError: StateFlow<String?> = _toastError

    init {
        getEthnicities()
        getBabies()
    }

    fun updateBabyGender(gender: Baby.Gender) {
        _babyGender.value = gender
    }

    fun updateEthnicitySelected(selected: String) {
        _ethnicitySelected.value = selected
    }

    fun dismissError() {
        _toastError.value = null
    }

    private fun getEthnicities() = viewModelScope.launch(dispatcherProvider.background()) {
        repository.getEthnicities().collectLatest {
            _ethnicities.value = it
        }
    }

    fun getRandomBaby() = viewModelScope.launch(dispatcherProvider.background()) {
        _babyGender.value?.let {
            repository.getRandomBaby(it, _ethnicitySelected.value).collectLatest {
                _babyState.value = it
            }
        } ?: run {
            _toastError.value = "You must select gender before generate a baby!"
        }
    }

    private fun getBabies() = viewModelScope.launch(dispatcherProvider.background()) {
        repository.getBabies().collectLatest {
            _babiesState.value = it
        }
    }
}