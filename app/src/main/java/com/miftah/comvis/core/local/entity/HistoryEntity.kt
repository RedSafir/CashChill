package com.miftah.comvis.core.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("history_entity")
data class HistoryEntity(
    @PrimaryKey(true)
    @ColumnInfo(name = "id")
    val idn : Int = 0,

    @ColumnInfo(name = "description")
    val description : String,

    @ColumnInfo(name = "date")
    val date : String,

    @ColumnInfo(name = "money")
    val money : String,

    @ColumnInfo(name = "isAdded")
    val isAdded : Boolean
)