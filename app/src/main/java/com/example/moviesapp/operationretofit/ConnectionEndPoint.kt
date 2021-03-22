package com.example.moviesapp.operationretofit

import com.example.moviesapp.latestmoviesfragment.LatestMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ConnectionEndPoint {

    @GET("movie/popular")
    suspend fun getLatestMoves( @Query("api_key") api_key : String) : Response<LatestMoviesResponse>

}