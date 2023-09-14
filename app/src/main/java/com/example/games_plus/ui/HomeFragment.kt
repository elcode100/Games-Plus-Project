package com.example.games_plus.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.games_plus.R
import com.example.games_plus.adapter.HomeAdapter
import com.example.games_plus.databinding.FragmentHomeBinding
import com.example.games_plus.ui.viewmodels.AuthViewModel
import com.example.games_plus.ui.viewmodels.MainViewModel

class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding
    private val authViewModel: AuthViewModel by activityViewModels()
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recGames.setHasFixedSize(true)




        viewModel.loadAllGames()
        viewModel.loadFavoriteGames()
        addObservers()






        binding.btnLogout.setOnClickListener {
            authViewModel.logout()
        }

        authViewModel.currentUser.observe(viewLifecycleOwner) {
            if (it == null) {
                findNavController().navigate(R.id.loginFragment)
            }
        }
    }




    @SuppressLint("NotifyDataSetChanged")
    fun addObservers() {


        val adapter = HomeAdapter(this.requireContext(), emptyList(), viewModel)
        binding.recGames.adapter = adapter

        viewModel.dataList.observe(viewLifecycleOwner) { games ->
            adapter.dataset = games
            adapter.notifyDataSetChanged()
        }

    }


}


