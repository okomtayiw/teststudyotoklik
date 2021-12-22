package com.otoklikwiyatmoko.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.otoklikwiyatmoko.data.Repository
import com.otoklikwiyatmoko.model.Content
import com.otoklikwiyatmoko.model.Result
import com.otoklikwiyatmoko.model.blogsItem
import com.otoklikwiyatmoko.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ContentViewModels @Inject constructor(
    private val repository: Repository

) : ViewModel() {

    var dataContent: MutableLiveData<NetworkResult<Result>> = MutableLiveData()
    var dataContentDelete: MutableLiveData<NetworkResult<blogsItem>> = MutableLiveData()

    suspend fun getBlogs() {
        val response = repository.remote.getBlog()
        dataContent.value = handleResponseBlogs(response)
    }

    private fun handleResponseBlogs(response: Response<Result>): NetworkResult<Result>? {

        val dataResponse = response.body()
        return if (dataResponse != null) {

            NetworkResult.Success(dataResponse)
        } else {

            NetworkResult.Error(message = "Not Data")

        }

    }

    suspend fun deleteBlog(id: Int, content: Content) {
        val response = repository.remote.deleteBlog(id, content)
        dataContentDelete.value = handleResponseDelete(response)
    }

    private fun handleResponseDelete(response: Response<blogsItem>): NetworkResult<blogsItem>? {
        val dataResponse = response.body()
        return if (dataResponse != null) {

            NetworkResult.Success(dataResponse)
        } else {

            NetworkResult.Error(message = "Not Data")

        }
    }


}