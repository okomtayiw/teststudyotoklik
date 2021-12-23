package com.otoklixwiyatmoko.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.otoklixwiyatmoko.data.Repository
import com.otoklixwiyatmoko.model.Content
import com.otoklixwiyatmoko.model.blogsItem
import com.otoklixwiyatmoko.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelBlogPost @Inject constructor(
    private val repository: Repository
): ViewModel() {

    var dataContent: MutableLiveData<NetworkResult<blogsItem>> = MutableLiveData()


    suspend fun postBlog(content: Content) {
        val response = repository.remote.blogPost(content)
        dataContent.value = handleResponseBlogs(response)
    }

    private fun handleResponseBlogs(response: Response<blogsItem>): NetworkResult<blogsItem>? {
        val dataResponse = response.body()
        return if (dataResponse != null) {

            NetworkResult.Success(dataResponse)
        } else {

            NetworkResult.Error(message = "Error Api")

        }
    }

    suspend fun updateBlogItem(id: Int, content: Content){
        val response = repository.remote.updateBlog(id,content)
        dataContent.value = handleResponseUpdateBlogs(response)
    }


    private fun handleResponseUpdateBlogs(response: Response<blogsItem>): NetworkResult<blogsItem>? {
        val dataResponse = response.body()
        return if (dataResponse != null) {

            NetworkResult.Success(dataResponse)
        } else {

            NetworkResult.Error(message = "Error Api")

        }
    }
}


