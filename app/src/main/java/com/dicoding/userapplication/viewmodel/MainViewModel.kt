package com.dicoding.userapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.userapplication.repository.data.remote.response.GitHubResponse
import com.dicoding.userapplication.repository.data.remote.response.ItemsItem
import com.dicoding.userapplication.repository.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val listUser = MutableLiveData<List<ItemsItem>>()
    val getListUser: LiveData<List<ItemsItem>> = listUser

    private val isLoading = MutableLiveData<Boolean>()
    val getIsLoading: LiveData<Boolean> = isLoading

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        searchUser("Arif")
    }

    fun searchUser(username: String) {
        try {
            isLoading.value = true
            val client = ApiConfig.getApiService().getUser(username)
            client.enqueue(object: Callback<GitHubResponse>{
                override fun onResponse(
                    call: Call<GitHubResponse>,
                    response: Response<GitHubResponse>
                ) {
                    isLoading.value = false
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        listUser.value = response.body()?.items
                    }
                }

                override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
                    isLoading.value = false
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                }

            })
        } catch (e: Exception) {
            Log.d("Token e", e.toString())
        }
    }
}