package com.example.moviesapp.latestmoviesfragment

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.moviesapp.Constants
import com.example.moviesapp.R
import com.example.moviesapp.databinding.LatestMoviesItemBinding
import com.example.moviesapp.datailsmoviesfragment.DetailsMoviesFragment
import com.example.moviesapp.operationroomdb.AppDataBase
import com.example.moviesapp.operationroomdb.MoviesDao
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LatestMoviesAdapter(var dataSet: List<Result>, var onClickListener : OnMoviesItemClickListener,
                          var context: Context,
                          var onClick : OnClickListener) : RecyclerView.Adapter<LatestMoviesAdapter.ViewHolder>() {

    // BaseUrl fro operation photo
    companion object{
        var BASE_URL = "https://image.tmdb.org/t/p/w500"
    }


    class ViewHolder(var binding: LatestMoviesItemBinding ) : RecyclerView.ViewHolder(binding.root) {

        // Fixed checkBox problem for select tow item where we need select one item only
        var checkBoxStateArray = SparseBooleanArray()


        // Initialize fun for data from model
        fun initialize( dataSet: Result , action : OnMoviesItemClickListener ){

            binding.toggleImButtonId.isChecked = checkBoxStateArray.get(adapterPosition , false)

            binding.tvTitleLatestMoviesId.text  = dataSet.title
            Picasso.get().load(BASE_URL+dataSet.poster_path).into(binding.ivPosterMoviesId)

            // onClickListener for heart button
            binding.toggleImButtonId.setOnClickListener {

                if(! checkBoxStateArray.get(adapterPosition , false)){

                    action.onMoviesClick(dataSet , adapterPosition)
                    binding.toggleImButtonId.isChecked = true
                    checkBoxStateArray.put(adapterPosition , true)


                }else{
                    binding.toggleImButtonId.isChecked = false
                    checkBoxStateArray.put(adapterPosition , false)
                    CoroutineScope(Dispatchers.IO).async {

                        // Call fun delete item from favorite
                        var dataBase : AppDataBase = Room.databaseBuilder(itemView.context , AppDataBase::class.java , Constants.ROOM_DB_NAME).build()

                        CoroutineScope(Dispatchers.Main).async {
                            dataBase.moviesDao().deleteItems(dataSet.title)
                        }
                    }
                    //Toast.makeText(itemView.context , "Un Save ${dataSet.title}" , Toast.LENGTH_SHORT).show()
                }
            }
        }

        // for open details fragment
        fun initOnClickListener( action : OnClickListener){
            binding.cardLayoutId.setOnClickListener {
                action.onClickListener()
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

        // Get element from your dataSet at this position and replace the
        // contents of the view with that element


        // Call function initialize
        viewHolder.initialize(dataSet.get(position) , onClickListener)

        // Call function initOnClickListener
        viewHolder.initOnClickListener(onClick)

        // Save check box for favorite select after off app and on again
        CoroutineScope(Dispatchers.IO).launch{

            var dataBase : AppDataBase = Room.databaseBuilder( context , AppDataBase::class.java,Constants.ROOM_DB_NAME).build()

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
    }


    // Return the size of your dataSet (invoked by the layout manager)
    override fun getItemCount() = dataSet.size


    // The interface for click on the toggle button
    interface OnMoviesItemClickListener{
        fun onMoviesClick(dataSet : Result , position: Int)
    }

    // The interface for click on item open details fragment
    interface OnClickListener{
        fun onClickListener()
    }


}