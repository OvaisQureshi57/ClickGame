package com.example.clickgame

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface FirebaseInterface {
    @GET("jellyCount/jellyClicker.json")
    fun getCount(): Call<Int>

    @PUT("jellyCount/jellyClicker.json")
    fun updateCount(@Body count: Int): Call<Void>
}