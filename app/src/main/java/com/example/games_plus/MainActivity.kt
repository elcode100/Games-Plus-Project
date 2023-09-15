package com.example.games_plus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.games_plus.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.navController

        binding.bottomNavBar.setupWithNavController(navController)

        setupActionBarWithNavController(navController)
        val config = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.navigation_home,
                R.id.navigation_library,
                R.id.loginFragment,
                R.id.registerFragment
            )
        )

        setupActionBarWithNavController(navController, config)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id == R.id.fragment_detail
                || destination.id == R.id.loginFragment
                || destination.id == R.id.registerFragment
                || destination.id == R.id.forgotPasswordFragment)
            {
                binding.bottomNavBar.visibility = View.GONE

            } else {

                binding.bottomNavBar.visibility = View.VISIBLE


            }


        }








    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }



}