package com.ddd.airplane.repository.database.member

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.ddd.airplane.common.base.BaseDao
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MemberDao : BaseDao<MemberEntity> {

    @Query("SELECT * FROM MemberEntity")
    suspend fun select(): MemberEntity

    @Query("DELETE FROM MemberEntity")
    suspend fun deleteAll()

    @Transaction
    suspend fun memberInsert(entity: MemberEntity) {
        deleteAll()
        insert(entity)
    }
}