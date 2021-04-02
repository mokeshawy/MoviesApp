package com.example.moviesapp.favoritesfragment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.moviesapp.Constants
import com.example.moviesapp.databinding.FavoriteMoviesItemBinding
import com.example.moviesapp.latestmoviesfragment.MoviesModel
import com.example.moviesapp.operationroomdb.AppDataBase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteMoviesAdapter (private val dataSet: List<MoviesModel> , var context: Context) : RecyclerView.Adapter<FavoriteMoviesAdapter.ViewHolder>() {

    class ViewHolder(var binding: FavoriteMoviesItemBinding) : RecyclerView.ViewHolder(binding.root) {


    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        var myViewHolder = ViewHolder(FavoriteMoviesItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))

        return myViewHolder

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.binding.tvTitleLatestMoviesId.text = dataSet[position].title
        Picasso.get().load(dataSet[position].poster_path).into(viewHolder.binding.ivPosterMoviesId)

        // Save check box for favorite select after off app and on again
        CoroutineScope(Dispatchers.IO).launch{

            var dataBase : AppDataBase = Room.databaseBuilder( context , AppDataBase::class.java, Constants.ROOM_DB_NAME).build()

            CoroutineScope(Dispatchers.Main).launch {

                var data =  dataBase.moviesDao().getAllFav()

                for( select in data){

                    var title = select.title

                    if(title == dataSet[position].title ) {

                        viewHolder.binding.toggleImButtonId.isChecked = true
                    }
                }
            }
        }

        // UnaSave item in favorite page
        viewHolder.binding.toggleImButtonId.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {

                // Call fun delete item from favorite
                var dataBase : AppDataBase = Room.databaseBuilder(context , AppDataBase::class.java , Constants.ROOM_DB_NAME).build()

                CoroutineScope(Dispatchers.Main).async {
                    dataBase.moviesDao().deleteItems(dataSet[position].title)
                }
            }
            //Toast.makeText(context,"Un Save ${dataSet[position].title}",Toast.LENGTH_SHORT).show()
        }
    }

    // Return the size of your dataSet (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}