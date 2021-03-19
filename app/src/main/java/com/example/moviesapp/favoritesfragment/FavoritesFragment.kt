package com.example.moviesapp.favoritesfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

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
    }
}