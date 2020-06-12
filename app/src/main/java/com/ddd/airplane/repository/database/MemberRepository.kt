package com.ddd.airplane.repository.database

import com.ddd.airplane.common.base.BaseRepository
import com.ddd.airplane.repository.database.member.MemberEntity
import com.ddd.airplane.repository.database.room.RoomManager

/**
 * MemberRepository for Coroutine
 */
object MemberRepository : BaseRepository() {

    private val db = RoomManager.instance.memberDao()

    /**
     * 회원 정보 삭제
     */
    suspend fun deleteAll() {
        db.deleteAll()
    }

    /**
     * 회원 정보 세팅
     */
    suspend fun insertMember(entity: MemberEntity) {
        db.memberInsert(entity)
    }

    /**
     * 회원정보 불러오기
     */
    suspend fun select() =
        db.select()

}