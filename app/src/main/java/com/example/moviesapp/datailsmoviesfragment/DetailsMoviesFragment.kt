package com.example.moviesapp.datailsmoviesfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentDetailsMoviesBinding
import com.example.moviesapp.latestmoviesfragment.LatestMoviesAdapter
import com.squareup.picasso.Picasso

class DetailsMoviesFragment : Fragment() {

    lateinit var binding    : FragmentDetailsMoviesBinding
    val moviesDetailsArgs   : DetailsMoviesFragmentArgs by navArgs()
    val detailsViewModel    : DetailsViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsMoviesBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.detailsVarModel = detailsViewModel

        Picasso.get().load(LatestMoviesAdapter.BASE_URL+moviesDetailsArgs.moviesDetails.poster_path).into(binding.ivPosterMoviesId)
        binding.tvTitleMovies.text  = moviesDetailsArgs.moviesDetails.title
        binding.tvDateMovies.text   = moviesDetailsArgs.moviesDetails.release_date
        binding.tvOverview.text     = moviesDetailsArgs.moviesDetails.overview

        binding.tlBarDetails.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)
        binding.tlBarDetails.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_detailsMoviesFragment_to_viewPagerFragment)
        }
    }
}