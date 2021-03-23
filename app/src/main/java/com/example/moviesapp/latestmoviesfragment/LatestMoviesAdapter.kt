package com.example.moviesapp.latestmoviesfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.LatestMoviesItemBinding
import com.squareup.picasso.Picasso

class LatestMoviesAdapter(var dataSet: List<Result>, var onClickListener : OnMoviesItemClickListener) : RecyclerView.Adapter<LatestMoviesAdapter.ViewHolder>() {

    // BaseUrl fro operation photo
    companion object{
        var BASE_URL = "https://image.tmdb.org/t/p/w500"
    }

    class ViewHolder(var binding: LatestMoviesItemBinding ) : RecyclerView.ViewHolder(binding.root) {

        // Initialize fun for data from model
        fun initialize( dataSet: Result , action : OnMoviesItemClickListener ){

            binding.tvTitleLatestMoviesId.text    = dataSet.title
            Picasso.get().load(BASE_URL+dataSet.poster_path).into(binding.ivPosterMoviesId)

            // Set onClick for itemView
            binding.toggleImButtonId.setOnClickListener {
                action.onMoviesClick(dataSet , adapterPosition)
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

        viewHolder.initialize(dataSet.get(position) , onClickListener)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size


    // The interface for click on the item
    interface OnMoviesItemClickListener{

        fun onMoviesClick(dataSet : Result , position: Int)
    }

}