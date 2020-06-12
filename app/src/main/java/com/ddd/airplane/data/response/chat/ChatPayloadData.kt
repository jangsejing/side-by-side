package com.ddd.airplane.data.response.chat

data class ChatPayloadData(
    val type: String?,
    val messageId: Long?,
    val roomId: Long?,
    val senderId: String?,
    val senderNickName: String?,
    val content: String?,
    val userCount: String?
)