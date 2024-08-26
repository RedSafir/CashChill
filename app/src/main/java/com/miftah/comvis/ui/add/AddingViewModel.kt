package com.miftah.comvis.ui.add

import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miftah.comvis.domain.FromScanned
import com.miftah.comvis.domain.Repository
import com.miftah.comvis.domain.Scanned
import com.miftah.comvis.ui.camera.CameraEvent
import com.miftah.comvis.ui.camera.CameraState
import com.miftah.comvis.utils.AppUtils.toUriOrNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddingViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {
    private val _state = mutableStateOf(AddingState())
    val state: State<AddingState> get() = _state

    fun onEvent(addingEvent: AddingEvent) {
        when (addingEvent) {

            is AddingEvent.TextFieldCount -> {
                _state.value = _state.value.copy(
                    count = addingEvent.count
                )
            }

            is AddingEvent.TextFieldDescription -> {
                _state.value = _state.value.copy(
                    count = addingEvent.description
                )
            }

            is AddingEvent.TextFieldMoney -> {
                _state.value = _state.value.copy(
                    count = addingEvent.money
                )
            }

            is AddingEvent.SaveToDB -> {
                viewModelScope.launch {
                    repository.saveData(
                        Scanned(
                            isAdded = true,
                            money = _state.value.money,
                            date = "07/08/2024",
                            description = _state.value.description
                        )
                    )
                }
            }
        }
    }

    fun inputImageAndValue(fromScanned: FromScanned) {
        _state.value = _state.value.copy(
            money = fromScanned.money,
            uri = fromScanned.uri.toUriOrNull()
        )
    }

}