package com.ddd.airplane.data.response.chat

/**
 * 채팅방 메세지 목록
 */
data class ChatMessageData(
    val messages: ArrayList<MessageData>?
) {
    /**
     * 메세지
     */
    data class MessageData(
        var messageId: Long?,
        var roomId: Long?,
        var senderId: String?,
        var content: String?,
        val createAt: String?
    )
}

