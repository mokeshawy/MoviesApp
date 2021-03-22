package com.example.moviesapp.operationroomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.moviesapp.latestmoviesfragment.MoviesModel

@Dao
interface MoviesDao {

    @Insert
    suspend fun insertFavorite(moviesModel: MoviesModel)

    @Query("SELECT * FROM MoviesModel")
    suspend fun getAllFav() : List<MoviesModel>

    @Query("SELECT * FROM MoviesModel WHERE title =:title")
    suspend fun getTitle( title : String) : List<MoviesModel>
}