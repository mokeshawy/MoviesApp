package com.example.moviesapp.latestmoviesfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentLatestMoviesBinding
import com.example.moviesapp.latestmoviesfragment.LatestMoviesAdapter.Companion.BASE_URL
import com.example.moviesapp.operationroomdb.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlin.Result

class LatestMoviesFragment : Fragment() , LatestMoviesAdapter.OnMoviesItemClickListener{

    lateinit var binding        : FragmentLatestMoviesBinding
    val latestMoviesViewModel   : LatestMoviesFragmentViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
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

         // Show response from API in recycler view
        latestMoviesViewModel.viewDataForLatestMovies()
        latestMoviesViewModel.moviesDetails.observe(viewLifecycleOwner, Observer {
            binding.rcViewMoviesId.adapter = LatestMoviesAdapter(it.results , this)
            
        })
    }

    override fun onMoviesClick(dataSet: com.example.moviesapp.latestmoviesfragment.Result, position: Int) {

            CoroutineScope(Dispatchers.IO).async {
                var dataBase : AppDataBase = Room.databaseBuilder(requireActivity() , AppDataBase::class.java,"FavoriteMovies").build()
                var insertFavMovies = MoviesModel(dataSet.title , BASE_URL+dataSet.poster_path)
                CoroutineScope(Dispatchers.Main).async {
                    var title = dataBase.moviesDao().getTitle(dataSet.title )
                    if(title.size == 1){

                        Toast.makeText(context , "${dataSet.title} Already Save to favorite" , Toast.LENGTH_SHORT).show()
                    }else{
                        dataBase.moviesDao().insertFavorite(insertFavMovies)
                        Toast.makeText(context , "Save ${dataSet.title}" , Toast.LENGTH_SHORT).show()
                    }
                }
            }

    }
}