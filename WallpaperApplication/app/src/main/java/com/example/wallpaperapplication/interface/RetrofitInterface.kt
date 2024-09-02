package com.example.wallpaperapplication.`interface`


import com.example.wallpaperapplication.model.WallpaperRvModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitInterface {
    @Headers("Authorization: fTReBZBeq8NLm3ngQli3UmvPn5k4Kf9O9IfQdedtWcrjWpz3stt55Bzd")
    @GET("curated?per_page=30")
    fun getWallpapers(
        @Query("page") page: Int,
    ): Call<WallpaperRvModel>?

    @Headers("Authorization: fTReBZBeq8NLm3ngQli3UmvPn5k4Kf9O9IfQdedtWcrjWpz3stt55Bzd")
    @GET("search?")
    fun getWallpaperByCategory(
        @Query("query") category: String,
        @Query("per_page") per_page: Int,
        @Query("page") page: Int,
    ): Call<WallpaperRvModel>?

}