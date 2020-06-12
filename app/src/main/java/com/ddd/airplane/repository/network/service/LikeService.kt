package com.ddd.airplane.repository.network.service

import com.ddd.airplane.data.response.chat.ProgramData
import retrofit2.Response
import retrofit2.http.*

/**
 * @author jess
 */
interface LikeService {

    /**
     * 좋아요 조회
     */
    @GET("/api/v1/subjects/{subjectId}/like")
    suspend fun getLike(

    ): Response<List<ProgramData>>

    /**
     * 좋아요
     */
    @POST("/api/v1/subjects/{subjectId}/like")
    suspend fun postLike(
        @Path("subjectId") subjectId: Long
    ): Response<Unit>

    /**
     * 좋아요 취소
     */
    @DELETE("/api/v1/subjects/{subjectId}/like")
    suspend fun deleteLike(
        @Path("subjectId") subjectId: Long
    ): Response<Unit>


}