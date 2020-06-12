package com.ddd.airplane.repository.network.service

import com.ddd.airplane.data.response.SubscribeData
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

/**
 * @author jess
 */
interface SubscribeService {

    /**
     * 구독한 채팅방 조회
     */
    @GET("/api/v1/roomsOfSubscribedSubjects")
    suspend fun getSubscribe(
        @Query("pageNum") pageNum: Int
    ): Response<SubscribeData>

    /**
     * 구독
     */
    @POST("/api/v1/subjects/{subjectId}/subscribe")
    suspend fun postSubscribe(
        @Path("subjectId") subjectId: Long
    ): Response<Unit>

    /**
     * 구독 취소
     */
    @DELETE("/api/v1/subjects/{subjectId}/subscribe")
    suspend fun deleteSubscribe(
        @Path("subjectId") subjectId: Long
    ): Response<Unit>

}