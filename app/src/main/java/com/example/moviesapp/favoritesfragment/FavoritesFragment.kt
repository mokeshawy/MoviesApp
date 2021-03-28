package com.example.moviesapp.favoritesfragment

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment(){

    lateinit var binding    : FragmentFavoritesBinding
    val favoritesViewModel  : FavoritesFragmentViewModel by viewModels()
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Connect whit viewModel
        binding.lifecycleOwner = this
        binding.favoritesVarModel = favoritesViewModel


        // Operation work for viewPager2
        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager_fragmentId)
        view.setOnClickListener {
            viewPager?.currentItem = 2
        }

        // Call function for show data from room database
        favoritesViewModel.showFavData(requireActivity())
        favoritesViewModel.dataForFavMovies.observe(viewLifecycleOwner, Observer {
            binding.rcViewMoviesId.adapter = FavoriteMoviesAdapter(it , requireActivity())
        })

        // Call fun for refresh
        favoritesViewModel.refresh(requireActivity() , binding.swipeLayout)
    }
}