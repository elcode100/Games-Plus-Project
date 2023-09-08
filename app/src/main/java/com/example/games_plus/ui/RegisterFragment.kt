package com.example.games_plus.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.games_plus.R
import com.example.games_plus.databinding.FragmentRegisterBinding
import com.example.games_plus.ui.viewmodels.AuthViewModel


class  RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val authViewModel: AuthViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBack.setOnClickListener {

            findNavController().navigate(R.id.loginFragment)

        }

        binding.btnRegister.setOnClickListener {

            val email: String = binding.tietEmail.text.toString()
            val password: String = binding.tietPass.text.toString()

            if (email != "" && password != "") {

                authViewModel.signUp(email, password)

            }

        }

        authViewModel.currentUser.observe(viewLifecycleOwner) {

            if (it != null) {

                findNavController().navigate(R.id.navigation_home)
            }
        }

    }

}