package com.dicoding.userapplication.repository.data.remote.retrofit

import com.dicoding.userapplication.repository.data.remote.response.DetailUserResponse
import com.dicoding.userapplication.repository.data.remote.response.GitHubResponse
import com.dicoding.userapplication.repository.data.remote.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getUser(
        @Query("q") id: String
    ): Call<GitHubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<ItemsItem>>
}
