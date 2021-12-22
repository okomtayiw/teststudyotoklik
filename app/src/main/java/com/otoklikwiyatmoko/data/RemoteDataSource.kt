package com.otoklikwiyatmoko.data

import com.otoklikwiyatmoko.data.network.BlogApi
import com.otoklikwiyatmoko.model.Content
import com.otoklikwiyatmoko.model.Result
import com.otoklikwiyatmoko.model.blogsItem
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val blogApi: BlogApi
) {
    suspend fun getBlog(): Response<Result> {
        return blogApi.getBlog()
    }

    suspend fun getDetail(id: Int): Response<blogsItem>{
        return blogApi.getDetailPost(id)
    }

    suspend fun blogPost(content: Content): Response<blogsItem>{
        return blogApi.postBlog(content)
    }

    suspend fun deleteBlog(id: Int, content: Content): Response<blogsItem>{
        return blogApi.deleteBlog(id, content)
    }

    suspend fun updateBlog(id: Int, content: Content): Response<blogsItem>{
        return blogApi.updateBlog(id, content)
    }
}