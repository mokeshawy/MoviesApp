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
import com.example.moviesapp.operationroomdb.AppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.text.FieldPosition

class LatestMoviesFragment : Fragment()  {

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
            binding.rcViewMoviesId.adapter = LatestMoviesAdapter(it.results , requireActivity())
            
        })
    }
}