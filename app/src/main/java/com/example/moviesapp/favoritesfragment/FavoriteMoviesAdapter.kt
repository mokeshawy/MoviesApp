package com.example.moviesapp.favoritesfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.FavoriteMoviesItemBinding
import com.example.moviesapp.latestmoviesfragment.MoviesModel
import com.squareup.picasso.Picasso

class FavoriteMoviesAdapter (private val dataSet: List<MoviesModel>) : RecyclerView.Adapter<FavoriteMoviesAdapter.ViewHolder>() {

    class ViewHolder(var binding: FavoriteMoviesItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {

        }
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
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}