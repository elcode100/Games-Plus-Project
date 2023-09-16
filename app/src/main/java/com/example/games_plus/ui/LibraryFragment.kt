package com.example.games_plus.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.games_plus.adapter.LibraryAdapter
import com.example.games_plus.databinding.FragmentLibraryBinding
import com.example.games_plus.ui.viewmodels.MainViewModel

class LibraryFragment : Fragment() {

    private lateinit var binding: FragmentLibraryBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addObservers()
        viewModel.loadFavoriteGames()



    }




    private fun addObservers() {
        val adapter = LibraryAdapter(requireContext(), emptyList(), viewModel)
        binding.recLibrary.adapter = adapter


        viewModel.favoriteGames.observe(viewLifecycleOwner) {

            binding.recLibrary.adapter = LibraryAdapter(this.requireContext(), it, viewModel)

        }
    }




}





/*
val window = activity?.window
window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.black)*/
