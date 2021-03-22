package com.example.moviesapp.latestmoviesfragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.moviesapp.databinding.LatestMoviesItemBinding
import com.example.moviesapp.operationroomdb.AppDataBase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class LatestMoviesAdapter(private val dataSet: List<Result> , var context: Context) : RecyclerView.Adapter<LatestMoviesAdapter.ViewHolder>() {

    companion object{
        var BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
    class ViewHolder(var binding: LatestMoviesItemBinding ) : RecyclerView.ViewHolder(binding.root) {

        init {

        }
    }

    // Interface onclick listener
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        var myViewHolder = ViewHolder(LatestMoviesItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false) )

        return myViewHolder

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int ) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.binding.tvTitleLatestMoviesId.text    = dataSet[position].title
        Picasso.get().load(BASE_URL+dataSet[position].poster_path).into(viewHolder.binding.ivPosterMoviesId)

        viewHolder.binding.toggleImButtonId.setOnClickListener {

            if(viewHolder.binding.toggleImButtonId.isChecked){

                CoroutineScope(Dispatchers.IO).async {
                    var dataBase : AppDataBase = Room.databaseBuilder(context , AppDataBase::class.java,"FavoriteMovies").build()
                    var insertFavMovies = MoviesModel(dataSet[position].title , BASE_URL+dataSet[position].poster_path)
                    CoroutineScope(Dispatchers.Main).async {
                        var title = dataBase.moviesDao().getTitle(dataSet[position].title)
                        if(title.size == 1){
                            Toast.makeText(context , "${dataSet[position].title} Save to favorite" , Toast.LENGTH_SHORT).show()
                        }else{
                            dataBase.moviesDao().insertFavorite(insertFavMovies)
                        }
                    }
                }
                Toast.makeText(context , "Save ${dataSet[position].title}" , Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context , "${dataSet[position].title} UnSaved" , Toast.LENGTH_SHORT).show()
            }

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}