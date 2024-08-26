package com.miftah.comvis.ui.camera

import android.content.Context
import android.net.Uri

sealed class CameraEvent {
    data class ScanToCLod(val data: Uri, val context : Context) : CameraEvent()
}