package com.otoklixwiyatmoko.data

import com.otoklixwiyatmoko.data.network.BlogApi
import com.otoklixwiyatmoko.model.Content
import com.otoklixwiyatmoko.model.Result
import com.otoklixwiyatmoko.model.BlogsItem
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val blogApi: BlogApi
) {
    suspend fun getBlog(): Response<Result> {
        return blogApi.getBlog()
    }

    suspend fun getDetail(id: Int): Response<BlogsItem>{
        return blogApi.getDetailPost(id)
    }

    suspend fun blogPost(content: Content): Response<BlogsItem>{
        return blogApi.postBlog(content)
    }

    suspend fun deleteBlog(id: Int, content: Content): Response<BlogsItem>{
        return blogApi.deleteBlog(id, content)
    }

    suspend fun updateBlog(id: Int, content: Content): Response<BlogsItem>{
        return blogApi.updateBlog(id, content)
    }
}