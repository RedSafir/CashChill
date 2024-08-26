package com.miftah.comvis.utils

import android.net.Uri
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URISyntaxException

object AppUtils {
    fun fromListToString(list: List<String?>): String {
        return if (!list.isNullOrEmpty()) {
            Gson().toJson(list)
        } else {
            ""
        }
    }

    fun fromStringToList(value: String?): List<String> {
        return if (!value.isNullOrEmpty()) {
            val listType = object : TypeToken<List<String>>() {}.type
            Gson().fromJson(value, listType)
        } else {
            emptyList()
        }
    }

    fun String.toUriOrNull(): Uri? {
        return try {
            Uri.parse(this)
        } catch (e: URISyntaxException) {
            null
        }
    }

    fun Uri.toStringSafe(): String? {
        return try {
            this.toString()
        } catch (e: UnsupportedOperationException) {
            null
        }
    }
}