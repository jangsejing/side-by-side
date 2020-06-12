package com.ddd.airplane.repository.database.recent

import androidx.room.Dao
import androidx.room.Query
import com.ddd.airplane.common.base.BaseDao

@Dao
interface RecentDao : BaseDao<RecentEntity> {

    @Query("SELECT * FROM RecentEntity")
    suspend fun selectAll(): List<RecentEntity>

    @Query("SELECT * FROM RecentEntity ORDER BY timeStamp DESC LIMIT 1 ")
    suspend fun selectTopLimit(): RecentEntity?

}