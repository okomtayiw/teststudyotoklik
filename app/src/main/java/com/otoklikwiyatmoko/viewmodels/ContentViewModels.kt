package com.otoklikwiyatmoko.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.otoklikwiyatmoko.data.Repository
import com.otoklikwiyatmoko.model.Result
import com.otoklikwiyatmoko.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ContentViewModels @Inject constructor(
    private val repository: Repository

) : ViewModel() {

    var dataContent: MutableLiveData<NetworkResult<Result>> = MutableLiveData()


    suspend fun getBlogs()  {
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
}