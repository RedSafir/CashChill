package com.miftah.comvis.ui.add

import android.graphics.Bitmap
import android.net.Uri

data class AddingState(
    val money : String = "",
    val count : String = "",
    val description : String = "Text",
    val uri : Uri? = null
)