package com.example.moviesapp.favoritesfragment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.example.moviesapp.latestmoviesfragment.MoviesModel
import com.example.moviesapp.operationroomdb.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesFragmentViewModel : ViewModel() {

    var dataForFavMovies = MutableLiveData<List<MoviesModel>>()

    fun showFavData( context: Context){

        CoroutineScope(Dispatchers.IO).launch{

            var dataBase : AppDataBase = Room.databaseBuilder( context , AppDataBase::class.java,"FavoriteMovies").build()

            CoroutineScope(Dispatchers.Main).launch {

                dataForFavMovies.value = dataBase.moviesDao().getAllFav()
            }
        }
    }
}