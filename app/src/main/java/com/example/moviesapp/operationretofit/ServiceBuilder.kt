package com.example.moviesapp.operationretofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    //const val BASE_URL = "https://api.themoviedb.org/3/"

    fun makeRetrofit() : ConnectionEndPoint{

        return Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ConnectionEndPoint::class.java)
    }

}