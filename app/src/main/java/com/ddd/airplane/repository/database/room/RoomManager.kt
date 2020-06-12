package com.ddd.airplane.repository.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ddd.airplane.repository.database.member.MemberDao
import com.ddd.airplane.repository.database.member.MemberEntity
import com.ddd.airplane.repository.database.recent.RecentDao
import com.ddd.airplane.repository.database.recent.RecentEntity

@Database(entities = [MemberEntity::class, RecentEntity::class], version = 1, exportSchema = false)
abstract class RoomManager : RoomDatabase() {

    companion object {

        lateinit var instance: RoomManager

        fun init(context: Context) {
            instance = Room.databaseBuilder(
                context,
                RoomManager::class.java,
                "airplane.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        fun close() {
            instance.close()
        }
    }

    abstract fun memberDao(): MemberDao
    abstract fun recentDao(): RecentDao
}