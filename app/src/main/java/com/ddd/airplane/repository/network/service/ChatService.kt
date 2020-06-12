package com.ddd.airplane.repository.network.service

import com.ddd.airplane.data.response.RecentData
import com.ddd.airplane.data.response.chat.ChatMessageData
import com.ddd.airplane.data.response.chat.ChatRoomData
import com.ddd.airplane.repository.network.config.ServerUrl
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author jess
 */
interface ChatService {

    /**
     * 채팅방 조회
     */
    @GET(ServerUrl.GET_ROOM_INFO)
    suspend fun getRoomInfo(
        @Path("roomId") roomId: Long
    ): Response<ChatRoomData>

    /**
     * 채팅방 메세지 조회
     */
    @GET(ServerUrl.GET_ROOM_MESSAGE)
    suspend fun getRoomMessages(
        @Path("roomId") roomId: Long,
        @Query("baseMessageId") baseMessageId: Int,
        @Query("size") size: Int,
        @Query("direction") direction: String
    ): Response<ChatMessageData>

    /**
     * 최근 채팅방 조회
     */
    @GET(ServerUrl.GET_RECENT_ROOM)
    suspend fun getRecentRooms(
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int
    ): Response<RecentData>

}