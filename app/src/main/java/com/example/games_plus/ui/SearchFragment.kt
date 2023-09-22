package com.example.games_plus.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.games_plus.R
import com.example.games_plus.adapter.SearchAdapter
import com.example.games_plus.databinding.FragmentSearchBinding
import com.example.games_plus.ui.viewmodels.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchFragment : Fragment() {

    private lateinit var binding : FragmentSearchBinding
    private val viewModel: MainViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recView = binding.recSearch
        recView.setHasFixedSize(true)

        addObservers()

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {


            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }


            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }


            override fun afterTextChanged(s: Editable?) {
                viewModel.searchGames(binding.editTextSearch.text.toString())
            }
        })


        binding.searchClear.setOnClickListener {

            binding.editTextSearch.text.clear()
        }

        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.bottom_nav_bar)
        bottomNav?.visibility = View.VISIBLE


    }



    private fun addObservers() {
        viewModel.searchList.observe(viewLifecycleOwner) {
            binding.recSearch.adapter = SearchAdapter(this.requireContext(), it, viewModel)

        }

    }



}