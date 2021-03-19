package com.example.moviesapp.viewpagerfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviesapp.R
import com.example.moviesapp.ViewPagerAdapter
import com.example.moviesapp.databinding.FragmentViewPagerBinding
import com.example.moviesapp.favoritesfragment.FavoritesFragment
import com.example.moviesapp.latestmoviesfragment.LatestMoviesFragment

class ViewPagerFragment : Fragment() {

    lateinit var binding : FragmentViewPagerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentViewPagerBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var fragmentList = arrayListOf<Fragment>(

            LatestMoviesFragment(),
            FavoritesFragment()
        )

        val adapter = ViewPagerAdapter(fragmentList , requireActivity().supportFragmentManager , lifecycle)

        binding.viewPagerFragmentId.adapter = adapter
    }
}