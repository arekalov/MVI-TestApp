package com.arekalov.mvi_app.data

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object FoxNetWorkService {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://randomfox.ca/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val foxService = retrofit.create(FoxApi::class.java)
    suspend fun getFox(): Response<Model> {
        return foxService.getFox()
    }

}

interface FoxApi {
    @GET("floof/")
    suspend fun getFox(): Response<Model>
}