package com.example.study.runningApp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.study.R
import com.example.study.databinding.ActivityMainRunningBinding
import com.example.study.runningApp.Constant.ACTION_SHOW_TRACKING_FRAGMENT
import com.example.study.runningApp.db.RunDao
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivityRunning : AppCompatActivity() {



    private lateinit var binding:ActivityMainRunningBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainRunningBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navigateToTrackingFragmentifNeeded(intent)
        bindViews()
    }

    private fun bindViews() {
        val navHostFragment =findNavController(R.id.navHostFragment)
        binding.bottomNavigationView.setupWithNavController(navHostFragment)
        navHostFragment?.addOnDestinationChangedListener(object :NavController.OnDestinationChangedListener{
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                when(destination.id){
                    R.id.settingsFragment,R.id.runFragment,R.id.staticsFragment -> binding.bottomNavigationView.visibility = View.VISIBLE
                    else -> binding.bottomNavigationView.visibility = View.GONE
                }
            }

        })
    }

    private fun navigateToTrackingFragmentifNeeded(intent: Intent){
        if(intent.action == ACTION_SHOW_TRACKING_FRAGMENT){
            findNavController(R.id.navHostFragment).navigate(R.id.action_global_trackingfragment)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { navigateToTrackingFragmentifNeeded(it) }
    }
}