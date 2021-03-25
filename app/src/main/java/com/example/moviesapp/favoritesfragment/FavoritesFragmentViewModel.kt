package com.example.moviesapp.favoritesfragment

import android.content.Context
import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.moviesapp.latestmoviesfragment.MoviesModel
import com.example.moviesapp.operationroomdb.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesFragmentViewModel : ViewModel() {

    var dataForFavMovies = MutableLiveData<List<MoviesModel>>()

    // Function select data from room database
    fun showFavData( context: Context ){

        CoroutineScope(Dispatchers.IO).launch{

            var dataBase : AppDataBase = Room.databaseBuilder( context , AppDataBase::class.java,"FavoriteMovies").build()

            CoroutineScope(Dispatchers.Main).launch {

                dataForFavMovies.value = dataBase.moviesDao().getAllFav()
            }
        }
    }

    // Refresh function for update select new data entry room database
    fun refresh(context: Context , swipeRefreshLayout : SwipeRefreshLayout){

        swipeRefreshLayout.setOnRefreshListener {

            showFavData(context)

            @Suppress("DEPRECATION")
            Handler().postDelayed(Runnable { swipeRefreshLayout.isRefreshing = false }, 2500)
        }
    }
}