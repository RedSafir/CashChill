package com.miftah.comvis.core.remote.dto

import com.google.gson.annotations.SerializedName

data class ScanResponse(

	@field:SerializedName("probability")
	val probability: String,

	@field:SerializedName("label")
	val label: String,

	@field:SerializedName("value")
	val value: Int
)
