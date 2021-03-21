package com.example.moviesapp.latestmoviesfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentLatestMoviesBinding

class LatestMoviesFragment : Fragment() , LatestMoviesAdapter.OnItemClickListener {

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

        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager_fragmentId)

        view.setOnClickListener {
            viewPager?.currentItem = 1
        }


        latestMoviesViewModel.viewDataForLatestMovies()
        latestMoviesViewModel.moviesDetails.observe(viewLifecycleOwner, Observer {
            binding.rcViewMoviesId.adapter = LatestMoviesAdapter(it.articles , this)
        })
    }

    override fun onItemClick(position: Int) {

        Toast.makeText(requireActivity() , position.toString() , Toast.LENGTH_SHORT).show()
    }

}