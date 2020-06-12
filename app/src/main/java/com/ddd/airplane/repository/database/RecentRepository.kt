package com.ddd.airplane.repository.database

import com.ddd.airplane.repository.database.recent.RecentEntity
import com.ddd.airplane.repository.database.room.RoomManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * RecentRepository for Coroutine
 */
object RecentRepository {

    private val db = RoomManager.instance.recentDao()

    /**
     * 최근 본 방송
     */
    suspend fun selectTopLimit() =
        db.selectTopLimit()


    /**
     * 최근 본 방송 삽입
     */
    suspend fun insertRecent(entity: RecentEntity) {
        db.insert(entity)
    }
}