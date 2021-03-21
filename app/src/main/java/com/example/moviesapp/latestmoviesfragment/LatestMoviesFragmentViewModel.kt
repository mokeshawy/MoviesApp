package com.example.moviesapp.latestmoviesfragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviesapp.operationretofit.ServiceBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class LatestMoviesFragmentViewModel : ViewModel(){

    var moviesDetails = MutableLiveData<LatestMoviesResponse>()


    fun viewDataForLatestMovies(){

        CoroutineScope(Dispatchers.IO).async {

            var response = ServiceBuilder.makeRetrofit().getLatestMoves("apple" , "2021-03-20" , "2021-03-20" , "popularity" , "9b3d814ad7e840fa97fa9608886787f5")

            CoroutineScope(Dispatchers.Main).async {

                moviesDetails.value = response.body()

            }
        }
    }
}