package com.codegameapp.foodfast.API

import com.codegameapp.foodfast.Data.DataFood
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface ApiFood {

    @GET("/food")
    suspend fun getFoods(): List<DataFood>

    companion object {
        private const val Url = "http://192.168.8.163:8080"
        fun create(): ApiFood {
            val api = Retrofit.Builder()
                .baseUrl(Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiFood::class.java)
            return api
        }
    }

}