package com.otoklixwiyatmoko.data.network

import com.otoklixwiyatmoko.model.Content
import com.otoklixwiyatmoko.model.Result
import com.otoklixwiyatmoko.model.blogsItem
import retrofit2.Response
import retrofit2.http.*

interface BlogApi {
    @GET("/posts")
    suspend fun getBlog() : Response<Result>

    @GET("/posts/{id}")
    suspend fun getDetailPost(@Path("id") id: Int) : Response<blogsItem>

    @POST("/posts")
    suspend fun postBlog(@Body request: Content) : Response<blogsItem>

    @PUT("/posts/{id}")
    suspend fun updateBlog(@Path("id") id: Int, @Body request: Content) : Response<blogsItem>

    @HTTP(method = "DELETE", path = "/posts/{id}", hasBody = true)
    suspend fun deleteBlog(@Path("id") id: Int, @Body request: Content) : Response<blogsItem>
}