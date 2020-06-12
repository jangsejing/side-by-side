package com.ddd.airplane.repository.network.service

import com.ddd.airplane.data.response.SearchData
import com.ddd.airplane.data.response.home.HomeData
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author jess
 */
interface GeneralService {

    /**
     * 홈 리스트 조회
     */
    @GET("/api/v1/home")
    suspend fun getHome(): Response<HomeData>

    /**
     * 검색
     */
    @GET("/api/v1/search")
    suspend fun getSearch(
        @Query("query") query: String,
        @Query("pageNum") pageNum: Int = 1,
        @Query("pageSize") pageSize: Int = 10
    ): Response<SearchData>

}