package com.miftah.comvis.domain

import com.miftah.comvis.core.local.room.AppDB
import com.miftah.comvis.core.remote.api.AppAPI
import com.miftah.comvis.utils.UiState
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File
import javax.inject.Inject

class Repository @Inject constructor(
    val api : AppAPI,
    val db :AppDB
) {
    fun scanImage(file: File) = flow {
        emit(UiState.Loading)
        try {
            val request = file.asRequestBody("image/*".toMediaTypeOrNull())
            val photo = MultipartBody.Part.createFormData("file", file.name, request)
            val result = api.scanImage(photo)
            emit(UiState.Success(result))
        } catch (e: HttpException) {
            emit(UiState.Error(e.message()))
        }
    }

    suspend fun saveData(data : Scanned) = db.historyDao().insert(data.toHistoryEntity())

    fun getData() = db.historyDao().getAll().map { it -> it.map { it.toScanned() } }
}