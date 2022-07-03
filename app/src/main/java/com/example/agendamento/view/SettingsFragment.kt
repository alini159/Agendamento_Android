package com.example.agendamento.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.agendamento.R
import com.example.agendamento.databinding.SettingsFragmentBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: SettingsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingsFragmentBinding.inflate(inflater, container, false)
        close()
        return binding.root
    }

    private fun close() {
        binding.button.setOnClickListener {
         findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
        }
    }
}

