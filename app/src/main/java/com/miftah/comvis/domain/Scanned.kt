package com.miftah.comvis.domain

import androidx.room.ColumnInfo
import com.miftah.comvis.core.local.entity.HistoryEntity
import com.miftah.comvis.core.remote.dto.ScanResponse

data class Scanned(
    val idn : Int = 0,
    val description : String,
    val date : String,
    val money : String,
    val isAdded : Boolean
)

fun Scanned.toHistoryEntity() : HistoryEntity {
    return HistoryEntity(
        date = "20/08/2024",
        description = description,
        money = money,
        isAdded = isAdded
    )
}

fun HistoryEntity.toScanned() : Scanned {
    return Scanned(
        date = date,
        description = description,
        money = money,
        isAdded = isAdded,
        idn = idn
    )
}