package com.miftah.comvis.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.miftah.comvis.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {
    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> get() = _state

    fun observe() {
        _state.value = _state.value.copy(
            histories = repository.getData()
        )
    }

    init {
        observe()
    }
}