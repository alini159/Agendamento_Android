package com.example.agendamento.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.agendamento.R
import com.example.agendamento.databinding.SplahScreenFragmentBinding

class SplashScreenFragment: Fragment() {

    private lateinit var binding: SplahScreenFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SplahScreenFragmentBinding.inflate(inflater,container, false)
        setupSplash()
        return binding.root
    }

    private fun setupSplash(){
        Handler().postDelayed({
            findNavController().navigate(R.id.action_splashScreenFragment_to_loginFragment)
        }, 2500)
    }
}