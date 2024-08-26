package com.miftah.comvis.ui.camera

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.miftah.comvis.domain.Repository
import com.miftah.comvis.utils.uriToFile
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {
    private val _state = mutableStateOf(CameraState())
    val state: State<CameraState> get() = _state

    fun onEvent(cameraEvent: CameraEvent) {
        when (cameraEvent) {
            is CameraEvent.ScanToCLod -> sendToCloud(cameraEvent.data, cameraEvent.context)
        }
    }

    fun sendToCloud(uri: Uri, context: Context) {
        _state.value = _state.value.copy(
            imageState = repository.scanImage(uriToFile(uri, context))
        )
    }
}