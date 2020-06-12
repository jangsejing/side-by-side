package com.ddd.airplane.common.base

import androidx.room.*
import io.reactivex.Completable

/**
 * Room BaseDAO
 * @author jess
 */
interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(obj: T)

    @Delete
    suspend fun delete(obj: T)

}

