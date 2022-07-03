package com.example.agendamento.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.agendamento.R
import com.example.agendamento.databinding.LoginFragmentBinding
import com.example.agendamento.model.UserResponse
import com.example.agendamento.viewModel.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: LoginFragmentBinding
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)
        binding.buttonLogin.setOnClickListener {
            goToDashboard()
        }
        return binding.root
    }

    private fun goToDashboard() {
        val login = binding.loginEmail.text.toString()
        val password = binding.loginCpf.text.toString()
        viewModel.authUser(requireActivity(), login, password)
        viewModel.user.observe(viewLifecycleOwner) { result ->
            when (result) {
                is UserResponse.Success -> findNavController().navigate(
                    R.id.action_loginFragment_to_homeFragment,
                    bundleOf("user" to result.data)
                )
                is UserResponse.Error -> Toast.makeText(context, result.error, Toast.LENGTH_LONG).show()
            }
        }
    }
}

