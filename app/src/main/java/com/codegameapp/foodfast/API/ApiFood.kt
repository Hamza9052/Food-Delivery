package com.codegameapp.foodfast.API

import com.codegameapp.foodfast.Data.MealResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface ApiFood {
    companion object{
        private const val Url ="https://www.themealdb.com/api/json/v1/1/"
        fun create(): ApiFood {
            val client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // Increase connection timeout
                .readTimeout(30, TimeUnit.SECONDS) // Increase read timeout
                .build()
            val api = Retrofit.Builder()
                .baseUrl(Url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiFood::class.java)
            return api
        }
    }


    interface TheMealDBApi {
        @GET("lookup.php")
        suspend fun getMealDetails(@Query("i") mealId: String): MealResponse
    }

}