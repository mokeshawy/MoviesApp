package com.example.moviesapp.latestmoviesfragment

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.moviesapp.databinding.LatestMoviesItemBinding
import com.example.moviesapp.operationroomdb.AppDataBase
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LatestMoviesAdapter(var dataSet: List<Result>, var onClickListener : OnMoviesItemClickListener , var context: Context) : RecyclerView.Adapter<LatestMoviesAdapter.ViewHolder>() {

    // BaseUrl fro operation photo
    companion object{
        var BASE_URL = "https://image.tmdb.org/t/p/w500"
    }


    class ViewHolder(var binding: LatestMoviesItemBinding ) : RecyclerView.ViewHolder(binding.root) {

        var checkBoxStateArray = SparseBooleanArray()

        // Initialize fun for data from model
        fun initialize( dataSet: Result , action : OnMoviesItemClickListener ){

            binding.toggleImButtonId.isChecked = checkBoxStateArray.get(adapterPosition , false)

            binding.tvTitleLatestMoviesId.text  = dataSet.title
            Picasso.get().load(BASE_URL+dataSet.poster_path).into(binding.ivPosterMoviesId)

            // Set onClick for itemView
            binding.toggleImButtonId.setOnClickListener {

                if(! checkBoxStateArray.get(adapterPosition , false)){

                    action.onMoviesClick(dataSet , adapterPosition)
                    binding.toggleImButtonId.isChecked = true
                    checkBoxStateArray.put(adapterPosition , true)

                }else{
                    binding.toggleImButtonId.isChecked = false
                    checkBoxStateArray.put(adapterPosition , false)
                    Toast.makeText(itemView.context , "Un Save ${dataSet.title}" , Toast.LENGTH_SHORT).show()
                }
            }
        }
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

        // Call function initialize

        viewHolder.initialize(dataSet.get(position) , onClickListener)

        CoroutineScope(Dispatchers.IO).launch{

            var dataBase : AppDataBase = Room.databaseBuilder( context , AppDataBase::class.java,"FavoriteMovies").build()

            CoroutineScope(Dispatchers.Main).launch {

              var data =  dataBase.moviesDao().getAllFav()

                for( select in data){

                    var id = select.id

                    if(id.toString().toInt() == position+1 ) {

                        viewHolder.binding.toggleImButtonId.isChecked = true
                    }
                }
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size


    // The interface for click on the item
    interface OnMoviesItemClickListener{
        fun onMoviesClick(dataSet : Result , position: Int)
    }

}