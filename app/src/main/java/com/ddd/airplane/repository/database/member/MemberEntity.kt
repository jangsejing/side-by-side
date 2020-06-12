package com.ddd.airplane.repository.database.member

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MemberEntity")
data class MemberEntity(
    @PrimaryKey
    var email: String, // 이메일
    var nickName: String // 닉네임
)