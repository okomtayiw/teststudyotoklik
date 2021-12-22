package com.otoklikwiyatmoko.data.network

import com.otoklikwiyatmoko.model.Result
import com.otoklikwiyatmoko.model.blogsItem
import retrofit2.Response
import retrofit2.http.GET

interface BlogApi {
    @GET("/posts")
    suspend fun getBlog() : Response<Result>

    @GET("/posts/{id}")
    suspend fun getDetailPost(id: Int) : Response<blogsItem>
}