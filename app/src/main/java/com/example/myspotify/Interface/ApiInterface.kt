package com.example.myspotify.Interface

import com.example.myspotify.models.MyData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {
    @Headers("x-rapidapi-key: e694ece25dmsh1abd34d68e0135dp1bbc3fjsn39db987e0205",
        "x-rapidapi-host: deezerdevs-deezer.p.rapidapi.com")

    @GET("search")
    fun getData(@Query("q") query: String): Call<MyData>
}