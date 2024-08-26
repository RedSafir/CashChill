package com.miftah.comvis.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FromScanned(
    val uri : String,
    val money : String
) : Parcelable
