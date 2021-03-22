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

            var response = ServiceBuilder.makeRetrofit().getLatestMoves( "342b8c3dcf19a60e32e83e086f6df5a5" )

            CoroutineScope(Dispatchers.Main).async {

                moviesDetails.value = response.body()

            }
        }
    }
}