package com.example.agendamento.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.agendamento.databinding.MoreOptionsFragmentBinding
import com.example.agendamento.viewModel.LoginViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MoreOptionsFragment : Fragment() {
    private lateinit var binding: MoreOptionsFragmentBinding
    private val database: FirebaseFirestore = Firebase.firestore
    private val viewModel: LoginViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MoreOptionsFragmentBinding.inflate(inflater, container, false)
        setupCard()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        insertListeners()
    }

    private fun setupCard() {
        if (viewModel.getCurretUser().cardInformation != null) {
            val doc = database.collection("CardInformation")
                .document(viewModel.getCurretUser().cardInformation.toString())

            doc.get().addOnSuccessListener { currentCard ->
                binding.moreOptionsTitleTextView.text =
                    currentCard.data?.getValue("title").toString()
                binding.moreOptionsDescriptionTextView.text =
                    currentCard.data?.getValue("details").toString()
            }
        }else{
            binding.moreOptionsTitleTextView.text = ""
            binding.moreOptionsDescriptionTextView.text = "Não há nada por aqui"
        }
    }

    private fun insertListeners() {
        binding.loginForgotToolbar.setOnClickListener {
            goToMoreOptions()
        }
    }

    private fun goToMoreOptions() {
        findNavController().popBackStack()
    }
}

