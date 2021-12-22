package com.otoklikwiyatmoko.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.otoklikwiyatmoko.data.Repository
import com.otoklikwiyatmoko.model.blogsItem
import com.otoklikwiyatmoko.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelBlogDetail @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var dataContent: MutableLiveData<NetworkResult<blogsItem>> = MutableLiveData()


    suspend fun getBlogsId(id: Int) {
        val response = repository.remote.getDetail(id)
        dataContent.value = handleResponseBlogs(response)
    }

    private fun handleResponseBlogs(response: Response<blogsItem>): NetworkResult<blogsItem>? {
        val dataResponse = response.body()
        return if (dataResponse != null) {

            NetworkResult.Success(dataResponse)
        } else {

            NetworkResult.Error(message = "Not Data")

        }
    }


}