package com.example.moviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.widget.ViewPager2
import com.example.moviesapp.databinding.ActivityMainBinding
import com.example.moviesapp.favoritesfragment.FavoritesFragment
import com.example.moviesapp.latestmoviesfragment.LatestMoviesFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        // Operation work on for fragment
        val navHostFragment : NavHostFragment   = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController   : NavController     = navHostFragment.navController


        // Mange show action bar
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when(destination.id){

                R.id.splashFragment         -> supportActionBar!!.hide()
                R.id.latestMoviesFragment   -> supportActionBar!!.hide()

                else-> supportActionBar!!.show()
            }
        }

    }
}