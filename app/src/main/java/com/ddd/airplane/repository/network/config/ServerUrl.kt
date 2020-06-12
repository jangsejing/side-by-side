package com.ddd.airplane.repository.network.config

object ServerUrl {

    const val GET_ROOM_INFO = "/api/v1/rooms/{roomId}"
    const val GET_ROOM_MESSAGE = "/api/v1/rooms/{roomId}/messages"
    const val GET_RECENT_ROOM = "/api/v1/recentMessagedRooms"
    const val WEB_SOCKET = "/ws/websocket"
    const val SUBSCRIBE_ROOM = "/topic/room/"
    const val SEND_MSG = "/app/room/"

}