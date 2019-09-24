package com.app.sunday.api

import com.app.sunday.models.User
import retrofit2.Call
import retrofit2.http.QueryMap
import retrofit2.http.GET


interface NetworkCalls {


    companion object {

        val BASE_URL = "https://api.github.com/"
    }

    //Call<List<NewsGetSet>> getnews();
    //Call<List<NewsGetSet>> getnews(@Query("api")api: String, @Query("Country")Country: String));
    @GET("users")
    fun getData(): Call<List<User>>


}