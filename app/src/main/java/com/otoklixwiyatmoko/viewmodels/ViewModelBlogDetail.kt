package com.otoklixwiyatmoko.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.otoklixwiyatmoko.data.Repository
import com.otoklixwiyatmoko.model.BlogsItem
import com.otoklixwiyatmoko.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ViewModelBlogDetail @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var dataContent: MutableLiveData<NetworkResult<BlogsItem>> = MutableLiveData()


    suspend fun getBlogsId(id: Int) {
        val response = repository.remote.getDetail(id)
        dataContent.value = handleResponseBlogs(response)
    }

    private fun handleResponseBlogs(response: Response<BlogsItem>): NetworkResult<BlogsItem>? {
        val dataResponse = response.body()
        return if (dataResponse != null) {

            NetworkResult.Success(dataResponse)
        } else {

            NetworkResult.Error(message = "Not Data")

        }
    }


}