package com.example.agendamento.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.agendamento.R
import com.example.agendamento.databinding.HomeFragmentBinding
import com.example.agendamento.model.CardInformation
import com.example.agendamento.model.User
import com.example.agendamento.viewModel.LoginViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private lateinit var user: User
    private val database: FirebaseFirestore = Firebase.firestore
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        stateUser()
        setupUI()
        goToMoreDescription()
        return binding.root
    }

    private fun stateUser() {
        if (arguments?.getSerializable("user") != null) {
            user = arguments?.getSerializable("user") as User
        } else {
            user = viewModel.getCurretUser()
        }
    }

    private fun setupUI() {
        val doc = database.collection("Patient").document(user.cpf.toString())

        doc.get().addOnSuccessListener { task ->
            user.toNextConsult(task.data?.getValue("nextConsult").toString())
            user.toCardInformation(task.data?.getValue("currentCard").toString())
            viewModel.defineCurrentUser(user)
            binding.usernameTextView.text = user.name
        }
    }

    private fun goToMoreDescription() {
        binding.buttonMoreOptions.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_moreOptionsFragment)
        }
    }
}


