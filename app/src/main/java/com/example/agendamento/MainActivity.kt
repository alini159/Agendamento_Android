package com.example.agendamento

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.agendamento.databinding.ActivityMainBinding
import com.example.agendamento.viewModel.LoginViewModel

class MainActivity : AppCompatActivity() {

    val model: LoginViewModel by viewModels()

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val controller by lazy {
        findNavController(R.id.activity_main_navhost)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.mainBottomNavigation.setupWithNavController(controller)

        controller.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home, R.id.navigation_schedule, R.id.navigation_settings -> {
                    visibilityButtonNavigation()
                }
                else -> {
                    hideButtonNavigation()
                }
            }
        }
    }

    private fun visibilityButtonNavigation() {
        binding.mainBottomNavigation.visibility = View.VISIBLE
    }

    private fun hideButtonNavigation() {
        binding.mainBottomNavigation.visibility = View.GONE
    }

    override fun onBackPressed() {
        when (controller.currentDestination?.id) {
             R.id.splashFragment -> {
                super.onBackPressed()
            }
        }
    }
}




