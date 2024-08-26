package com.miftah.comvis.ui.home

import com.miftah.comvis.core.local.entity.HistoryEntity
import com.miftah.comvis.domain.Scanned
import kotlinx.coroutines.flow.Flow

data class HomeState(
    val histories : Flow<List<Scanned>>? = null
)