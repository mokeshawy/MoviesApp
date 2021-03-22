package com.example.moviesapp.operationroomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviesapp.latestmoviesfragment.MoviesModel

@Database( entities = [MoviesModel::class] , version = 1)
abstract class AppDataBase : RoomDatabase(){

    abstract fun moviesDao() : MoviesDao
}