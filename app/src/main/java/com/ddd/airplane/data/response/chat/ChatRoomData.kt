package com.ddd.airplane.data.response.chat

/**
 * 채팅방 정보
 */
data class ChatRoomData(
    val liked: Boolean? = false,
    var subjectSubscribed: Boolean? = false,
    val upcomingSubjectSchedule: ScheduleData? = null,
    val recentMessages: ArrayList<ChatMessageData.MessageData>? = null
) : ProgramData()