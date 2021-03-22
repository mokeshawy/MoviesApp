package com.example.moviesapp.latestmoviesfragment

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoviesModel (

    @ColumnInfo(name = "title")
    var title : String,

    @ColumnInfo(name = "poster_path")
    var poster_path : String

){

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}