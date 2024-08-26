package com.miftah.comvis.core.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.miftah.comvis.core.local.entity.HistoryEntity

@Database(
    entities = [HistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDB : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}