package com.miftah.comvis.ui.camera

import com.miftah.comvis.core.remote.dto.ScanResponse
import com.miftah.comvis.utils.UiState
import kotlinx.coroutines.flow.Flow

data class CameraState(
    val imageState: Flow<UiState<ScanResponse>>? = null,
)