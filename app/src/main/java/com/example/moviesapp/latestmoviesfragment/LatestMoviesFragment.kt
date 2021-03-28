package com.example.moviesapp.latestmoviesfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesapp.Constants
import com.example.moviesapp.OptionBuilder
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentLatestMoviesBinding
import com.example.moviesapp.latestmoviesfragment.LatestMoviesAdapter.Companion.BASE_URL
import com.example.moviesapp.operationroomdb.AppDataBase
import com.example.moviesapp.viewpagerfragment.ViewPagerFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class LatestMoviesFragment : Fragment() , LatestMoviesAdapter.OnMoviesItemClickListener , LatestMoviesAdapter.OnClickListener{

    lateinit var binding        : FragmentLatestMoviesBinding
    val latestMoviesViewModel   : LatestMoviesFragmentViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = FragmentLatestMoviesBinding.inflate(inflater)
        return binding.root
    }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Connect whit view model
        binding.lifecycleOwner          = this
        binding.latestMoviesVarModel    = latestMoviesViewModel


        // Operation work for viewPager2
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager_fragmentId)
        view.setOnClickListener {
            viewPager?.currentItem = 1
        }

         // Show progress dialog
         OptionBuilder.showProgressDialog(resources.getString(R.string.please_wait) , requireActivity())
         // Show response from API in recycler view
        latestMoviesViewModel.viewDataForLatestMovies()
        latestMoviesViewModel.moviesDetails.observe(viewLifecycleOwner, Observer {
            binding.rcViewMoviesId.adapter = LatestMoviesAdapter(it.results , this , requireActivity() , this)
            OptionBuilder.hideProgressDialog()
        })


    }

    override fun onMoviesClick(dataSet: com.example.moviesapp.latestmoviesfragment.Result, position: Int) {
            CoroutineScope(Dispatchers.IO).async {

                var dataBase : AppDataBase = Room.databaseBuilder(requireActivity() , AppDataBase::class.java, Constants.ROOM_DB_NAME).build()
                var insertFavMovies = MoviesModel(dataSet.title , BASE_URL+dataSet.poster_path)

                CoroutineScope(Dispatchers.Main).async {
                    var title = dataBase.moviesDao().getTitle(dataSet.title )
                    // Check item add already in favorite or no
                    if(title.size == 1){
                        //Toast.makeText(context , "${dataSet.title} Already Save to favorite" , Toast.LENGTH_SHORT).show()
                    }else{
                        dataBase.moviesDao().insertFavorite(insertFavMovies)
                        //Toast.makeText(context , "Save ${dataSet.title}" , Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    // OnClick for select movies open on details fragment
    override fun onClickListener( dataSet: Result , position: Int) {
        var movies = ViewPagerFragmentDirections.actionViewPagerFragmentToDetailsMoviesFragment(dataSet)
        findNavController().navigate(movies)
    }
}