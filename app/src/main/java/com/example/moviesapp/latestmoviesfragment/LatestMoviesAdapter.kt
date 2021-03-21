package com.example.moviesapp.latestmoviesfragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapp.databinding.LatestMoviesItemBinding
import com.squareup.picasso.Picasso

class LatestMoviesAdapter(private val dataSet: List<Article>, var onItemClickListener : OnItemClickListener) :
        RecyclerView.Adapter<LatestMoviesAdapter.ViewHolder>() {

    class ViewHolder(var binding: LatestMoviesItemBinding , onItemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.toggalImButtonId.setOnClickListener {

                onItemClickListener.onItemClick(adapterPosition +1)

            }
        }
    }

    // Interface onclick listener
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        var myViewHolder = ViewHolder(LatestMoviesItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false) , onItemClickListener)

        return myViewHolder

    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.binding.tvTitleLatestMoviesId.text    = dataSet[position].title
        Picasso.get().load(dataSet[position].urlToImage).into(viewHolder.binding.ivPosterMoviesId)


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}