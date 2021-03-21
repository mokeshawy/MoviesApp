package com.example.moviesapp.operationretofit

import com.example.moviesapp.latestmoviesfragment.LatestMoviesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ConnectionEndPoint {

    @GET("v2/everything")
    suspend fun getLatestMoves(@Query("q") q : String ,
                               @Query("api-from") from : String ,
                               @Query("to") to : String ,
                               @Query("sortBy") sortBy : String ,
                               @Query("apiKey") apiKey : String) : Response<LatestMoviesResponse>

}