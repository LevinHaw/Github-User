package com.dicoding.userapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.userapplication.repository.data.remote.response.ItemsItem
import com.dicoding.userapplication.repository.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {

    private val _following = MutableLiveData<List<ItemsItem>?>()
    val following: LiveData<List<ItemsItem>?> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun setUserLogin(userLogin: String) {
        _isLoading.value = true
        val call = ApiConfig.getApiService().getFollowing(userLogin)
        call.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>, response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _following.value = response.body()
                } else {
                    Log.d("Log","No response for following in ${userLogin}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.d("Log",userLogin)
            }
        })
    }

    companion object {
        private const val TAG = "FollowingViewModel"
    }
}