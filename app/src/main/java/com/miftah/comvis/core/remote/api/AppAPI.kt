package com.miftah.comvis.core.remote.api

import com.miftah.comvis.core.remote.dto.ScanResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AppAPI {
    @Multipart
    @POST("/predict")
    suspend fun scanImage(@Part file: MultipartBody.Part): ScanResponse
}